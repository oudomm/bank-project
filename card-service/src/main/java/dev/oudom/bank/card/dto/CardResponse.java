package dev.oudom.bank.card.dto;

import dev.oudom.bank.card.domain.CardStatus;
import dev.oudom.bank.card.domain.CardType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record CardResponse(
        UUID id,
        UUID customerId,
        String cardNumber,
        CardType cardType,
        BigDecimal creditLimit,
        CardStatus status,
        LocalDate expiryDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
