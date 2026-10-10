package Backend.FinTech.dto.request.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class PhoneNumberUpdateRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message="Phone Number Cannot Be Blank")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message ="Invalid Phone Number")
    
    private String phoneNumber;
}
