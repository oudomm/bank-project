package dev.oudom.bank.card.service.impl;

import dev.oudom.bank.card.domain.Card;
import dev.oudom.bank.card.domain.CardStatus;
import dev.oudom.bank.card.domain.CardType;
import dev.oudom.bank.card.dto.CardResponse;
import dev.oudom.bank.card.dto.CreateCardRequest;
import dev.oudom.bank.card.dto.UpdateCardRequest;
import dev.oudom.bank.card.exception.CardOperationException;
import dev.oudom.bank.card.exception.ResourceNotFoundException;
import dev.oudom.bank.card.mapper.CardMapper;
import dev.oudom.bank.card.repository.CardRepository;
import dev.oudom.bank.card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final Random random = new Random();

    @Override
    @Transactional
    public CardResponse create(CreateCardRequest request) {
        if (request.cardType() == CardType.CREDIT && request.creditLimit() == null) {
            throw new CardOperationException("Credit limit is required for CREDIT cards");
        }
        if (request.cardType() == CardType.DEBIT && request.creditLimit() != null) {
            throw new CardOperationException("Credit limit cannot be set for DEBIT cards");
        }

        String cardNumber = generateUniqueCardNumber();
        LocalDate expiryDate = LocalDate.now().plusYears(3);

        Card card = cardRepository.save(cardMapper.toEntity(request, cardNumber, expiryDate));
        return cardMapper.toResponse(card);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse findById(UUID id) {
        return cardMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> findByCustomerId(UUID customerId) {
        return cardRepository.findByCustomerId(customerId)
                .stream()
                .map(cardMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public CardResponse updateCreditLimit(UUID id, UpdateCardRequest request) {
        Card card = getOrThrow(id);
        if (card.getCardType() != CardType.CREDIT) {
            throw new CardOperationException("Credit limit can only be updated on CREDIT cards");
        }
        card.setCreditLimit(request.creditLimit());
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse block(UUID id) {
        Card card = getOrThrow(id);
        if (card.getStatus() == CardStatus.CANCELLED) {
            throw new CardOperationException("Cannot block a cancelled card");
        }
        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new CardOperationException("Card is already blocked");
        }
        card.setStatus(CardStatus.BLOCKED);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse unblock(UUID id) {
        Card card = getOrThrow(id);
        if (card.getStatus() == CardStatus.CANCELLED) {
            throw new CardOperationException("Cannot unblock a cancelled card");
        }
        if (card.getStatus() == CardStatus.ACTIVE) {
            throw new CardOperationException("Card is already active");
        }
        card.setStatus(CardStatus.ACTIVE);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public void cancel(UUID id) {
        Card card = getOrThrow(id);
        if (card.getStatus() == CardStatus.CANCELLED) {
            throw new CardOperationException("Card is already cancelled");
        }
        card.setStatus(CardStatus.CANCELLED);
        cardRepository.save(card);
    }

    private Card getOrThrow(UUID id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", id.toString()));
    }

    private String generateUniqueCardNumber() {
        String number;
        do {
            long part1 = (long) (random.nextDouble() * 10_000L);
            long part2 = (long) (random.nextDouble() * 10_000L);
            long part3 = (long) (random.nextDouble() * 10_000L);
            long part4 = (long) (random.nextDouble() * 10_000L);
            number = "%04d%04d%04d%04d".formatted(part1, part2, part3, part4);
        } while (cardRepository.existsByCardNumber(number));
        return number;
    }
}
