package Backend.FinTech.service.Interface.profile;

import Backend.FinTech.dto.request.profile.UpdateProfileRequest;
import Backend.FinTech.dto.response.profile.ProfileResponse;

public interface ProfileService {

    boolean updateProfile(UpdateProfileRequest request);
    ProfileResponse getProfile();
}
