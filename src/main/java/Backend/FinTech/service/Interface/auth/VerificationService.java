package Backend.FinTech.service.Interface.auth;

import Backend.FinTech.dto.request.auth.CodeRequest;
import org.springframework.http.ResponseEntity;


public interface VerificationService {
    ResponseEntity<String> generateVerificationCode(CodeRequest codeRequest);
    boolean verifyCode(String email, String code);
}
