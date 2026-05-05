package dev.oudom.bank.loan.service.impl;

import dev.oudom.bank.loan.domain.Loan;
import dev.oudom.bank.loan.domain.LoanStatus;
import dev.oudom.bank.loan.dto.CreateLoanRequest;
import dev.oudom.bank.loan.dto.LoanResponse;
import dev.oudom.bank.loan.exception.LoanOperationException;
import dev.oudom.bank.loan.exception.ResourceNotFoundException;
import dev.oudom.bank.loan.mapper.LoanMapper;
import dev.oudom.bank.loan.repository.LoanRepository;
import dev.oudom.bank.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final Random random = new Random();

    @Override
    @Transactional
    public LoanResponse apply(CreateLoanRequest request) {
        String loanNumber = generateUniqueLoanNumber();
        Loan loan = loanRepository.save(loanMapper.toEntity(request, loanNumber));
        return loanMapper.toResponse(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanResponse findById(UUID id) {
        return loanMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponse> findByCustomerId(UUID customerId) {
        return loanRepository.findByCustomerId(customerId)
                .stream()
                .map(loanMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public LoanResponse approve(UUID id) {
        Loan loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new LoanOperationException("Only PENDING loans can be approved, current status: " + loan.getStatus());
        }
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setStartDate(LocalDate.now());
        loan.setEndDate(LocalDate.now().plusMonths(loan.getTermMonths()));
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanResponse repay(UUID id, BigDecimal amount) {
        Loan loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new LoanOperationException("Only ACTIVE loans can receive repayments, current status: " + loan.getStatus());
        }
        if (amount.compareTo(loan.getOutstandingAmount()) > 0) {
            throw new LoanOperationException(
                    "Repayment amount exceeds outstanding balance of " + loan.getOutstandingAmount()
            );
        }
        loan.setOutstandingAmount(loan.getOutstandingAmount().subtract(amount));
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public LoanResponse close(UUID id) {
        Loan loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new LoanOperationException("Only ACTIVE loans can be closed, current status: " + loan.getStatus());
        }
        if (loan.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new LoanOperationException(
                    "Loan cannot be closed with outstanding balance of " + loan.getOutstandingAmount()
            );
        }
        loan.setStatus(LoanStatus.CLOSED);
        return loanMapper.toResponse(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public void cancel(UUID id) {
        Loan loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.PENDING) {
            throw new LoanOperationException("Only PENDING loans can be cancelled, current status: " + loan.getStatus());
        }
        loan.setStatus(LoanStatus.CANCELLED);
        loanRepository.save(loan);
    }

    private Loan getOrThrow(UUID id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan", "id", id.toString()));
    }

    private String generateUniqueLoanNumber() {
        String number;
        do {
            number = "LN%010d".formatted((long) (random.nextDouble() * 10_000_000_000L));
        } while (loanRepository.existsByLoanNumber(number));
        return number;
    }
}
