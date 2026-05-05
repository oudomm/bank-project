package dev.oudom.bank.loan.dto;

import dev.oudom.bank.loan.domain.LoanType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateLoanRequest(

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotNull(message = "Loan type is required")
        LoanType loanType,

        @NotNull(message = "Principal amount is required")
        @DecimalMin(value = "1000.00", message = "Principal amount must be at least 1,000.00")
        BigDecimal principalAmount,

        @NotNull(message = "Interest rate is required")
        @DecimalMin(value = "0.01", message = "Interest rate must be greater than 0")
        @DecimalMax(value = "100.00", message = "Interest rate must not exceed 100")
        BigDecimal interestRate,

        @NotNull(message = "Term in months is required")
        @Min(value = 1, message = "Term must be at least 1 month")
        @Max(value = 360, message = "Term must not exceed 360 months")
        Integer termMonths
) {}
