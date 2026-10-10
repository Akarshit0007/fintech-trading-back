package Backend.FinTech.controller.profile;

import Backend.FinTech.dto.request.profile.PhoneNumberUpdateRequest;
import Backend.FinTech.service.implementation.profile.ProfileServiceImp;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class ProfileController {
    private final ProfileServiceImp profileServiceImp;

    public ProfileController(ProfileServiceImp profileServiceImp) {
        this.profileServiceImp = profileServiceImp;
    }

    @PostMapping("/updatePhoneNumber")
    public Boolean updatePhoneNumber(@Valid @RequestBody PhoneNumberUpdateRequest phoneNumberUpdateRequest){
        return profileServiceImp.updatePhoneNumber(phoneNumberUpdateRequest);
    }

}
