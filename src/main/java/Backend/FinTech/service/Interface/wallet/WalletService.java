package Backend.FinTech.service.Interface.wallet;

import Backend.FinTech.dto.response.wallet.WalletResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface WalletService {
    WalletResponse getWalletByEmail(String email);
}
