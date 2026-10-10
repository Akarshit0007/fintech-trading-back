package Backend.FinTech.service.Interface.transaction;

import Backend.FinTech.dto.request.transaction.TransactionRequest;
import Backend.FinTech.dto.response.transaction.TransactionResponse;

public interface TransactionService {

    TransactionResponse processTransaction(TransactionRequest transactionRequest);
}
