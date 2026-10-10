package Backend.FinTech.repository;

import Backend.FinTech.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    Optional<Transactions> findByReferenceId(String referenceId);
}