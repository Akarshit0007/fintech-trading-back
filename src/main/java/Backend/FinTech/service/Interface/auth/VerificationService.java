package Backend.FinTech.service.Interface.auth;

import Backend.FinTech.dto.request.auth.CodeRequest;
import Backend.FinTech.dto.response.auth.CodeRespose;
import Backend.FinTech.dto.response.auth.CodeVerifierResponse;


public interface VerificationService {
    CodeRespose generateVerificationCode(CodeRequest codeRequest);
    CodeVerifierResponse verifyCode(String email, String code);
}
