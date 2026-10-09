package Backend.FinTech.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class PhoneNumberUpdateRequest {
    @NotBlank(message="Phone Number Cannot Be Blank")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message ="Invalid Phone Number")
    private String phoneNumber;
}
