package Backend.FinTech.service.Interface.auth;

import Backend.FinTech.dto.request.auth.CodeRequest;
import Backend.FinTech.dto.response.auth.CodeRespose;


public interface VerificationService {
    CodeRespose generateVerificationCode(CodeRequest codeRequest);
    boolean verifyCode(String email, String code);
}
