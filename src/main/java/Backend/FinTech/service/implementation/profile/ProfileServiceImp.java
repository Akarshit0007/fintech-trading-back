package Backend.FinTech.service.implementation.profile;

import Backend.FinTech.dto.request.profile.PhoneNumberUpdateRequest;
import Backend.FinTech.entity.Users;
import Backend.FinTech.repository.UserRepository;
import Backend.FinTech.service.Interface.profile.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImp implements ProfileService {
    private final UserRepository userRepository;

    public ProfileServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean updatePhoneNumber(PhoneNumberUpdateRequest request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPhoneNumber(request.getPhoneNumber());
        userRepository.save(user);
        return true;
    }
}