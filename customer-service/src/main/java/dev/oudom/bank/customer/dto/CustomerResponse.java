package dev.oudom.bank.customer.dto;

import dev.oudom.bank.customer.domain.CustomerStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address,
        CustomerStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
