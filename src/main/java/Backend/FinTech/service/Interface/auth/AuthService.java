package Backend.FinTech.service.Interface.auth;

import Backend.FinTech.dto.request.auth.LoginRequest;
import Backend.FinTech.dto.request.auth.SignupRequest;
import Backend.FinTech.dto.response.auth.AuthResponse;
import Backend.FinTech.dto.response.auth.UserResponse;
public interface AuthService {

 UserResponse signup(SignupRequest signupRequest);
 AuthResponse login(LoginRequest loginRequest);

}
