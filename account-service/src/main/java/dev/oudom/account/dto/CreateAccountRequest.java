package dev.oudom.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAccountRequest(
        @NotNull(message = "Customer id must not be null")
        UUID customerId,

        @NotBlank(message = "Account type must not be blank")
        String accountType
) {
}
