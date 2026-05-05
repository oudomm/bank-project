package dev.oudom.bank.card.mapper;

import dev.oudom.bank.card.domain.Card;
import dev.oudom.bank.card.dto.CardResponse;
import dev.oudom.bank.card.dto.CreateCardRequest;
import org.mapstruct.*;

import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CardMapper {

    @Mapping(target = "cardNumber", source = "cardNumber")
    @Mapping(target = "expiryDate", source = "expiryDate")
    @Mapping(target = "status", constant = "ACTIVE")
    Card toEntity(CreateCardRequest request, String cardNumber, LocalDate expiryDate);

    CardResponse toResponse(Card card);
}
