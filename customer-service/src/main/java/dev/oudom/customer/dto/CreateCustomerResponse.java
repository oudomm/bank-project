package dev.oudom.customer.dto;

import java.util.UUID;

public record CreateCustomerResponse(
        UUID id,
        String name,
        String email,
        String phoneNumber
) {
}
