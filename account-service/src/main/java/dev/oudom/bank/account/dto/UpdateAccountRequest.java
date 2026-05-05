package dev.oudom.bank.account.dto;

import dev.oudom.bank.account.domain.AccountStatus;
import dev.oudom.bank.account.domain.AccountType;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(

        @NotNull(message = "Account type is required")
        AccountType accountType,

        @NotNull(message = "Account status is required")
        AccountStatus status
) {}
