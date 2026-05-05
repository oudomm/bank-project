package dev.oudom.bank.loan.dto;

import dev.oudom.bank.loan.domain.LoanStatus;
import dev.oudom.bank.loan.domain.LoanType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record LoanResponse(
        UUID id,
        UUID customerId,
        String loanNumber,
        LoanType loanType,
        BigDecimal principalAmount,
        BigDecimal outstandingAmount,
        BigDecimal interestRate,
        Integer termMonths,
        LoanStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
