package Backend.FinTech.service.User.Interface;

import Backend.FinTech.dto.user.request.LoginRequest;
import Backend.FinTech.dto.user.request.SignupRequest;
import Backend.FinTech.dto.user.response.AuthResponse;
import Backend.FinTech.dto.user.response.UserResponse;
public interface AuthService {

 UserResponse signup(SignupRequest signupRequest);
 AuthResponse login(LoginRequest loginRequest);

}
