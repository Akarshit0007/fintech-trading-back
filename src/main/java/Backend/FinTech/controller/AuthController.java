package Backend.FinTech.controller;

import Backend.FinTech.dto.user.request.LoginRequest;
import Backend.FinTech.dto.user.request.SignupRequest;
import Backend.FinTech.dto.user.response.AuthResponse;
import Backend.FinTech.dto.user.response.UserResponse;
import Backend.FinTech.service.User.implementation.AuthServiceImp;
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
