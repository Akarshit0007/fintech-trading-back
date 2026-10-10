package Backend.FinTech.dto.response.auth;

import Backend.FinTech.enums.ErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeVerifierResponse {
    private String token;

}
