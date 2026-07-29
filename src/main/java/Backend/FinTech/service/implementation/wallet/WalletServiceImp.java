package Backend.FinTech.service.implementation.wallet;

import Backend.FinTech.dto.response.wallet.WalletResponse;
import Backend.FinTech.entity.Users;
import Backend.FinTech.entity.Wallet;
import Backend.FinTech.repository.WalletRepository;
import Backend.FinTech.service.Interface.wallet.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;

@Service
public class WalletServiceImp implements WalletService {

    private WalletRepository walletRepository;

    public WalletServiceImp(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }




    public void createInitialWallet(Users user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(BigDecimal.valueOf(10000.0));
        wallet.setCurrency("USD"); // Remove or change if your field name is different

        walletRepository.save(wallet);
    }

    @Override
    public WalletResponse getWalletByEmail(String email) { // <-- Must match interface
        Wallet wallet = walletRepository.getWalletByEmail(email)
                .orElseThrow(() -> new RuntimeException("Wallet not found for email: " + email));

        WalletResponse response = new WalletResponse();
        response.setBalance(wallet.getBalance());
        response.setCurrency(wallet.getCurrency());

        return response;
    }
}
