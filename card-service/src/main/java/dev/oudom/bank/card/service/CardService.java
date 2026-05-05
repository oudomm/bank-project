package dev.oudom.bank.card.service;

import dev.oudom.bank.card.dto.CardResponse;
import dev.oudom.bank.card.dto.CreateCardRequest;
import dev.oudom.bank.card.dto.UpdateCardRequest;

import java.util.List;
import java.util.UUID;

public interface CardService {

    CardResponse create(CreateCardRequest request);

    CardResponse findById(UUID id);

    List<CardResponse> findByCustomerId(UUID customerId);

    CardResponse updateCreditLimit(UUID id, UpdateCardRequest request);

    CardResponse block(UUID id);

    CardResponse unblock(UUID id);

    void cancel(UUID id);
}
