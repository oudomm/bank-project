package dev.oudom.bank.card.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateCardRequest(

        @NotNull(message = "Credit limit is required")
        @DecimalMin(value = "1.00", message = "Credit limit must be at least 1.00")
        BigDecimal creditLimit
) {}
