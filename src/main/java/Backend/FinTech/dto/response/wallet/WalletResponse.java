package Backend.FinTech.dto.response.wallet;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC) // <-- Make it public explicitly
@NoArgsConstructor
public class WalletResponse {
    private BigDecimal balance;
    private String currency;
}
