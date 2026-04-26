package dev.oudom.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(name = "UpdateCustomerRequest", description = "Request to update customer profile details")
public record UpdateCustomerRequest(
        @Schema(description = "Customer full name", example = "Oudom Dara")
        @NotBlank(message = "Name must not be blank")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @Schema(description = "Customer email address", example = "oudom@example.com")
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Email must be valid")
        String email,

        @Schema(description = "10-digit phone number", example = "9876543210")
        @NotBlank(message = "Mobile number must not be blank")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Mobile number must be exactly 10 digits"
        )
        String phoneNumber
) {
}
