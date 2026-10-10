package Backend.FinTech.service.Interface.profile;

import Backend.FinTech.dto.request.profile.PhoneNumberUpdateRequest;

public interface ProfileService {

    boolean updatePhoneNumber(PhoneNumberUpdateRequest request);
}
