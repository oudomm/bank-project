package dev.oudom.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "CustomerResponse", description = "Customer profile details")
public record CustomerResponse(
        @Schema(description = "Customer id")
        UUID id,
        @Schema(description = "Customer full name", example = "Oudom Dara")
        String name,
        @Schema(description = "Customer email address", example = "oudom@example.com")
        String email,
        @Schema(description = "10-digit phone number", example = "9876543210")
        String phoneNumber
) {
}
