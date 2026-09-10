package Backend.FinTech.controller.auth;

import Backend.FinTech.dto.request.auth.CodeRequest;
import Backend.FinTech.dto.response.auth.CodeRespose;
import Backend.FinTech.service.implementation.auth.VerificationServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class VerificationController {

    private final VerificationServiceImp verificationServiceImp;

    public  VerificationController(VerificationServiceImp verificationServiceImp) {
        this.verificationServiceImp = verificationServiceImp;
    }

    @PostMapping("/generate-code")
    public CodeRespose generateCode(@RequestBody CodeRequest codeRequest) {
        String email = codeRequest.getEmail();
        System.out.println(email);
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email Is Empty");
        }
           return verificationServiceImp.generateVerificationCode(codeRequest);
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        if (email == null || code == null) {
            return ResponseEntity.badRequest().body("Email and code are required");
        }

        boolean isVerified = verificationServiceImp.verifyCode(email, code);

        if (isVerified) {
            return ResponseEntity.ok("Email verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification code.");
        }
    }
}
