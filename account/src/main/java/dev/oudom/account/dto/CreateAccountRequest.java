package dev.oudom.account.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequest(
        @NotBlank(message = "Account type must not be blank")
        String accountType
) {
}
