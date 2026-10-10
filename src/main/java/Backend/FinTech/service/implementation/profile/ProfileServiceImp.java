package Backend.FinTech.service.implementation.profile;

import Backend.FinTech.dto.request.profile.UpdateProfileRequest;
import Backend.FinTech.dto.response.profile.ProfileResponse;
import Backend.FinTech.entity.Users;
import Backend.FinTech.repository.UserRepository;
import Backend.FinTech.service.Interface.profile.ProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImp implements ProfileService {
    private final UserRepository userRepository;

    public ProfileServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean updateProfile(UpdateProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = authentication.getName();

        Users user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());

        userRepository.save(user);
        return true;
    }

    @Override
    public ProfileResponse getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("Unauthorized: No valid JWT token found in security context");
        }

        String principalName = authentication.getName();
        System.out.println("--- DEBUG: JWT Principal Name extracted: " + principalName);

        // Depending on what your JWT subject contains, choose the correct repository lookup:

        // 1. If your JWT subject is the EMAIL address:
        Users user = userRepository.findByEmail(principalName)
                .orElseThrow(() -> new RuntimeException("User not found in database for email: " + principalName));

        // 2. If your JWT subject is an ID instead, uncomment this and comment out findByEmail above:
    /*
    Users user = userRepository.findById(Long.valueOf(principalName))
            .orElseThrow(() -> new RuntimeException("User not found in database for ID: " + principalName));
    */

        return new ProfileResponse(
                user.getEmail(),
                user.getPhoneNumber(),
                user.getName()
        );
    }
}