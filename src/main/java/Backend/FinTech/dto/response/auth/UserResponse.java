package Backend.FinTech.dto.response.auth;

import Backend.FinTech.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private UserRole role;

}
