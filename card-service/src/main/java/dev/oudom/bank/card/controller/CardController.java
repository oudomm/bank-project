package dev.oudom.bank.card.controller;

import dev.oudom.bank.card.dto.ApiResponse;
import dev.oudom.bank.card.dto.CardResponse;
import dev.oudom.bank.card.dto.CreateCardRequest;
import dev.oudom.bank.card.dto.UpdateCardRequest;
import dev.oudom.bank.card.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<ApiResponse<CardResponse>> create(@Valid @RequestBody CreateCardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Card issued successfully", cardService.create(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CardResponse>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Card retrieved", cardService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CardResponse>>> findByCustomerId(@RequestParam UUID customerId) {
        return ResponseEntity.ok(ApiResponse.ok("Cards retrieved", cardService.findByCustomerId(customerId)));
    }

    @PatchMapping("/{id}/credit-limit")
    public ResponseEntity<ApiResponse<CardResponse>> updateCreditLimit(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCardRequest request) {
        return ResponseEntity.ok(ApiResponse.ok("Credit limit updated", cardService.updateCreditLimit(id, request)));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<ApiResponse<CardResponse>> block(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Card blocked", cardService.block(id)));
    }

    @PatchMapping("/{id}/unblock")
    public ResponseEntity<ApiResponse<CardResponse>> unblock(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok("Card unblocked", cardService.unblock(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        cardService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
