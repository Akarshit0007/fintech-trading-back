package Backend.FinTech.service.implementation.auth;

import Backend.FinTech.dto.request.auth.LoginRequest;
import Backend.FinTech.dto.request.auth.SignupRequest;
import Backend.FinTech.dto.response.auth.AuthResponse;
import Backend.FinTech.dto.response.auth.UserResponse;
import Backend.FinTech.entity.Users;
import Backend.FinTech.exception.auth.AccountLockedException;
import Backend.FinTech.exception.auth.InvalidCredentialsException;
import Backend.FinTech.exception.user.UserNotFoundException;
import Backend.FinTech.mapper.UserMapper;

import Backend.FinTech.repository.UserRepository;
import Backend.FinTech.service.Interface.auth.AuthService;
import Backend.FinTech.service.implementation.wallet.WalletServiceImp;
import Backend.FinTech.util.JwtUtil;
import Backend.FinTech.util.PasswordUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImp implements AuthService {
private final UserRepository userRepository;
private final JwtUtil jwtUtil;
private  final PasswordUtil passwordUtil;
private final WalletServiceImp walletServiceImp;

    public AuthServiceImp(UserRepository userRepository, JwtUtil jwtUtil, PasswordUtil passwordUtil, WalletServiceImp walletServiceImp) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordUtil = passwordUtil;
        this.walletServiceImp = walletServiceImp;
    }

    @Override
    public UserResponse signup(SignupRequest signupRequest) {

        userRepository.findByEmail(signupRequest.getEmail())
                .ifPresent(users -> {
                    throw new UserNotFoundException(signupRequest.getEmail());
                });

        String encoded = passwordUtil.encode(signupRequest.getPassword());

        Users user = UserMapper.toEntity(signupRequest, encoded);

        Users savedUser = userRepository.save(user);

        walletServiceImp.createInitialWallet(savedUser);
        return UserMapper.toResponse(savedUser);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Users user=
                userRepository.findByEmail(loginRequest.getEmail())
                        .orElseThrow(() -> new UserNotFoundException(loginRequest.getEmail()));

//       Must Check Account status
                checkAccountLock(user);

        System.out.println("RAW: " + loginRequest.getPassword());
        System.out.println("DB: " + user.getPassword());

//      Password Checking
        boolean isMatch = passwordUtil.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );


        if (!isMatch) {
            handleFailedAttempts(user);
            throw new RuntimeException("Invalid Credentials");
        }

//      Success --> Reset Everything

        unlockUser(user);

        userRepository.save(user); // ✅ added



        String token = jwtUtil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail()).build();




    }

    public void handleFailedAttempts(Users user) {
    int MAX_ATTEMPTS = 3;
        if(!user.isAccountNonLocked()){
            throw new RuntimeException("Account is locked");

        }


        int attempts = user.getFailedAttempts()+1;
        user.setFailedAttempts(attempts);
        int remainingAttempts = MAX_ATTEMPTS - attempts;

        if (attempts >= MAX_ATTEMPTS) {
            lockUser(user);
            throw new InvalidCredentialsException("Invalid Credentials. " + remainingAttempts + " attempts remaining");
        }

        userRepository.save(user);
}

    private void lockUser(Users user) {
        user.setAccountNonLocked(false);
        user.setLockTime(LocalDateTime.now());
    }


    private void checkAccountLock(Users user) {

        if (user.isAccountNonLocked()) {
            return;
        }

        if (isLockExpired(user)) {
            unlockUser(user);
            userRepository.save(user);
            return;
        }

        throw new AccountLockedException(user.getEmail());
    }

    private void unlockUser(Users user) {
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
        user.setLockTime(null);
    }

    private boolean isLockExpired(Users user) {
        return user.getLockTime()
                .plusMinutes(5)
                .isBefore(LocalDateTime.now());
    }
}
