package Backend.FinTech.controller.auth;

import Backend.FinTech.dto.request.auth.LoginRequest;
import Backend.FinTech.dto.request.auth.SignupRequest;
import Backend.FinTech.dto.response.auth.AuthResponse;
import Backend.FinTech.dto.response.auth.UserResponse;
import Backend.FinTech.service.implementation.auth.AuthServiceImp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {


    private AuthServiceImp authServiceImp;


    AuthController(AuthServiceImp authServiceImp) {
        this.authServiceImp = authServiceImp;
    }


    @PostMapping("/signup")
    public UserResponse register(@RequestBody SignupRequest signupRequest){
         return authServiceImp.signup(signupRequest);

    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest){
        System.out.println("Email: " + loginRequest.getEmail());
        System.out.println("CALLED");
        return  authServiceImp.login(loginRequest);
    }


}
