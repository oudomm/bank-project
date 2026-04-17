package dev.oudom.account.dto;

import java.util.UUID;

public record CreateAccountResponse(
        UUID id,
        UUID customerId,
        Long accountNumber,
        String accountType
) {
}
