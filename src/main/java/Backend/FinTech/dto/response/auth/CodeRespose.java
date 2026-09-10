package Backend.FinTech.dto.response.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CodeRespose {
    public int verificationCode;
}
