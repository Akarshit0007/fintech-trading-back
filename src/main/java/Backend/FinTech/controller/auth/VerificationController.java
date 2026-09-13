package Backend.FinTech.controller.auth;

import Backend.FinTech.dto.request.auth.CodeRequest;
import Backend.FinTech.dto.request.auth.CodeVerifierRequest;
import Backend.FinTech.dto.response.auth.CodeRespose;
import Backend.FinTech.dto.response.auth.CodeVerifierResponse;
import Backend.FinTech.exception.auth.EmptyEmailException;
import Backend.FinTech.service.implementation.auth.VerificationServiceImp;
import Backend.FinTech.util.JwtUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            throw new EmptyEmailException("Email should Not Null");
        }
           return verificationServiceImp.generateVerificationCode(codeRequest);
    }

    @PostMapping("/verify-code")
    public CodeVerifierResponse verifyCode(@RequestBody CodeVerifierRequest codeVerifierRequest) {
        String email = codeVerifierRequest.getEmail();
        String code =  codeVerifierRequest.getVerificationCode();

        if (email == null || code == null) {
            throw new EmptyEmailException("Check Email Or Code Again");
        }

        return verificationServiceImp.verifyCode(email, code);
    }
}
