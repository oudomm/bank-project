package dev.oudom.bank.loan.repository;

import dev.oudom.bank.loan.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findByCustomerId(UUID customerId);

    boolean existsByLoanNumber(String loanNumber);
}
