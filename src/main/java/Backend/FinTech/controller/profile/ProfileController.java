package Backend.FinTech.controller.profile;

import Backend.FinTech.dto.request.profile.UpdateProfileRequest;
import Backend.FinTech.dto.response.profile.ProfileResponse;
import Backend.FinTech.service.Interface.profile.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile() {
        ProfileResponse response = profileService.getProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateProfile(@Valid @RequestBody UpdateProfileRequest request) {
        boolean success = profileService.updateProfile(request);
        return ResponseEntity.ok(success);
    }
}