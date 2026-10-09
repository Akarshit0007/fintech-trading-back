package Backend.FinTech.dto.response.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String email;
    private String phoneNumber;
    private String memberSince;
}
