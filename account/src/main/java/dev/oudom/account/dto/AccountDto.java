package dev.oudom.account.dto;

public record AccountDto(
        Long accountNumber,
        String accountType,
        String branchAddress
) {
}
