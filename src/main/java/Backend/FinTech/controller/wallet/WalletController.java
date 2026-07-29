package Backend.FinTech.controller.wallet;

import Backend.FinTech.dto.response.wallet.WalletResponse;
import Backend.FinTech.service.implementation.wallet.WalletServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/api")
public class WalletController {
    private WalletServiceImp walletServiceImp;


    public WalletController(WalletServiceImp walletServiceImp) {
        this.walletServiceImp = walletServiceImp;
    }

    @GetMapping("/wallet")
    public ResponseEntity<WalletResponse> wallet(Principal principal) {
        String userEmail = principal.getName();
        System.out.println(userEmail);

        // Call the correct method name (getWalletByEmail) using the interface
        WalletResponse response = walletServiceImp.getWalletByEmail(userEmail);

        return ResponseEntity.ok(response);
    }
}
