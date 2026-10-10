package Backend.FinTech.enums;

import lombok.Getter;

@Getter
public enum TransactionalStatus {
    PENDING, COMPLETED, FAILED, REFUNDED
}
