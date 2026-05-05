package dev.oudom.bank.account.dto;

import dev.oudom.bank.account.domain.AccountType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAccountRequest(

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotNull(message = "Account type is required")
        AccountType accountType
) {}
