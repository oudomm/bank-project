package dev.oudom.account.dto;

import java.util.UUID;

public record AccountResponse(
        UUID id,
        UUID customerId,
        Long accountNumber,
        String accountType
) {
}
