package Backend.FinTech.repository;

import Backend.FinTech.dto.response.wallet.WalletResponse;
import Backend.FinTech.entity.Wallet;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query("SELECT w FROM Wallet w WHERE w.user.email = :email")
    Optional<Wallet> getWalletByEmail(@Param("email") String email);
}
