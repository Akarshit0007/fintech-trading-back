package Backend.FinTech.service.implementation.transaction;

import Backend.FinTech.dto.request.transaction.TransactionRequest;
import Backend.FinTech.dto.response.transaction.TransactionResponse;
import Backend.FinTech.entity.Transactions;
import Backend.FinTech.entity.Wallet; // Import Wallet
import Backend.FinTech.enums.TransactionalStatus;
import Backend.FinTech.exception.transaction.InsufficientBalanceException;
import Backend.FinTech.repository.TransactionRepository;
import Backend.FinTech.repository.WalletRepository; // Import WalletRepository
import Backend.FinTech.service.Interface.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImp implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository; // Use WalletRepository instead of AccountRepository

    @Override
    @Transactional
    public TransactionResponse processTransaction(TransactionRequest transactionRequest) {

        // 1. Idempotency Check
        Transactions existingTransaction = transactionRepository.findByReferenceId(transactionRequest.getReferenceId()).orElse(null);
        if (existingTransaction != null) {
            return mapToResponse(existingTransaction);
        }

        // 2. Build and save initial transaction entity
        Transactions transaction = Transactions.builder()
                .referenceId(transactionRequest.getReferenceId())
                .userId(transactionRequest.getSenderId())
                .senderAccountId(transactionRequest.getSenderId())
                .receiverAccountId(transactionRequest.getReceiverId())
                .amountUSD(transactionRequest.getAmount())
                .status(TransactionalStatus.PENDING)
                .build();

        transaction = transactionRepository.save(transaction);

        try {
            // 3. Thread-Safe Wallet Lookups using Pessimistic Locks
            Wallet senderWallet = walletRepository.findByUserIdWithLock(transactionRequest.getSenderId())
                    .orElseThrow(() -> new IllegalArgumentException("Sender wallet not found for ID: " + transactionRequest.getSenderId()));

            Wallet receiverWallet = walletRepository.findByUserIdWithLock(transactionRequest.getReceiverId())
                    .orElseThrow(() -> new IllegalArgumentException("Receiver wallet not found for ID: " + transactionRequest.getReceiverId()));

            // 4. BALANCE CHECK: Ensure sender has enough funds
            if (senderWallet.getBalance().compareTo(transactionRequest.getAmount()) < 0) {
                throw new InsufficientBalanceException(
                        "Insufficient funds! Available balance: $" + senderWallet.getBalance() +
                                ", Attempted transfer: $" + transactionRequest.getAmount()
                );
            }

            // 5. MONEY TRANSFER: Deduct from sender and add to receiver
            senderWallet.setBalance(senderWallet.getBalance().subtract(transactionRequest.getAmount()));
            receiverWallet.setBalance(receiverWallet.getBalance().add(transactionRequest.getAmount()));

            walletRepository.save(senderWallet);
            walletRepository.save(receiverWallet);

            // 6. Mark transaction as successful
            transaction.setStatus(TransactionalStatus.COMPLETED);
            transaction.setUpdatedAt(LocalDateTime.now());

        } catch (InsufficientBalanceException e) {
            transaction.setStatus(TransactionalStatus.FAILED);
            transaction.setUpdatedAt(LocalDateTime.now());
            transactionRepository.save(transaction);
            throw e;

        } catch (Exception e) {
            transaction.setStatus(TransactionalStatus.FAILED);
            transaction.setUpdatedAt(LocalDateTime.now());
            transactionRepository.save(transaction);
            throw new RuntimeException("Transaction processing failed: " + e.getMessage());
        }

        transaction = transactionRepository.save(transaction);
        return mapToResponse(transaction);
    }

    private TransactionResponse mapToResponse(Transactions transaction) {
        String senderName = "Wallet " + transaction.getSenderAccountId();
        String receiverName = "Wallet " + transaction.getReceiverAccountId();

        return TransactionResponse.builder()
                .amount(transaction.getAmountUSD())
                .senderName(senderName)
                .receiverName(receiverName)
                .status(transaction.getStatus())
                .updatedAt(transaction.getUpdatedAt() != null ? transaction.getUpdatedAt() : transaction.getCreatedAt())
                .build();
    }
}