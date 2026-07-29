package Backend.FinTech.service.User.Interface;

import Backend.FinTech.dto.user.request.CodeRequest;
import org.springframework.http.ResponseEntity;


public interface VerificationService {
    ResponseEntity<String> generateVerificationCode(CodeRequest codeRequest);
    boolean verifyCode(String email, String code);
}
