package dev.oudom.bank.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name must not exceed 50 characters")
        String firstName,

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name must not exceed 50 characters")
        String lastName,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^\\+?[0-9]{9,15}$", message = "Phone must be 9–15 digits, optionally prefixed with +")
        String phone,

        @Size(max = 200, message = "Address must not exceed 200 characters")
        String address
) {}
