package Backend.FinTech.dto.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {

    private String referenceId;
    private Long senderId;
    private Long receiverId;
    private BigDecimal amount;


}
