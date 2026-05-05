package dev.oudom.bank.card.dto;

import dev.oudom.bank.card.domain.CardType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateCardRequest(

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotNull(message = "Card type is required")
        CardType cardType,

        @DecimalMin(value = "1.00", message = "Credit limit must be at least 1.00")
        BigDecimal creditLimit
) {}
