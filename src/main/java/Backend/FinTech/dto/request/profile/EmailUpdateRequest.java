package Backend.FinTech.dto.request.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailUpdateRequest {
    @NotBlank(message = "Email Cannot Be Blank")
    @Email(message = "Please provide a valid email address")
    private String email;
}
