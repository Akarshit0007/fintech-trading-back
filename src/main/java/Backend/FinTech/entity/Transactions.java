package Backend.FinTech.entity;

import Backend.FinTech.enums.TransactionalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "transactions_information", indexes = {
        @Index(name = "idx_reference_id", columnList = "referenceId"),
        @Index(name = "idx_user_id", columnList = "userId") // Added index for fast user history lookups
})
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String referenceId; // Idempotency key

    @Column(nullable = false)
    private Long userId; // The primary user viewing/owning this transaction history record

    @Column(nullable = false)
    private Long senderAccountId;

    @Column(nullable = false)
    private Long receiverAccountId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amountUSD;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionalStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = TransactionalStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}