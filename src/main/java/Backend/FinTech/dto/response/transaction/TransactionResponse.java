package Backend.FinTech.dto.response.transaction;

import Backend.FinTech.enums.TransactionalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private BigDecimal amount;
    private String senderName;
    private String receiverName;
    private TransactionalStatus status;
    private LocalDateTime updatedAt;
}
