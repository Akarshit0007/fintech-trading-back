package Backend.FinTech.entity;

import Backend.FinTech.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/* It is database Layer
   Contains full data (including sensitive fields)
   we shouldn't expose directly to client
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "users_information")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean AccountNonLocked;//When A user enter password more than 3 times account gets lock

    private int failedAttempts = 0 ;

    private LocalDateTime lockTime;



}
