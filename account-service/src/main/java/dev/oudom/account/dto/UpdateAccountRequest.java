package dev.oudom.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "UpdateAccountRequest", description = "Request to update account details")
public record UpdateAccountRequest(
        @Schema(description = "Account type", example = "Savings")
        @NotBlank(message = "Account type must not be blank")
        String accountType
) {
}
