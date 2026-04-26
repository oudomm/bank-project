package dev.oudom.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(name = "CreateAccountRequest", description = "Request to create a digital banking account")
public record CreateAccountRequest(
        @Schema(description = "Customer id that owns this account")
        @NotNull(message = "Customer id must not be null")
        UUID customerId,

        @Schema(description = "Account type. Defaults to Savings when omitted.", example = "Savings")
        String accountType
) {
}
