package dev.oudom.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "AccountResponse", description = "Digital banking account details")
public record AccountResponse(
        @Schema(description = "Internal account id")
        UUID id,
        @Schema(description = "Customer id that owns this account")
        UUID customerId,
        @Schema(description = "10-digit account number", example = "1234567890")
        Long accountNumber,
        @Schema(description = "Account type", example = "Savings")
        String accountType
) {
}
