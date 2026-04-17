package dev.oudom.account.dto;

import java.util.UUID;

public record AccountResponse(
        UUID id,
        Long accountNumber,
        String accountType
) {
}
