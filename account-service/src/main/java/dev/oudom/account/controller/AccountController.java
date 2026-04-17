package dev.oudom.account.controller;

import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.ApiResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import dev.oudom.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateAccountResponse>> createAccount(
            @Valid @RequestBody CreateAccountRequest createAccountRequest
    ) {
        CreateAccountResponse createAccountResponse = accountService.createAccount(createAccountRequest);
        ApiResponse<CreateAccountResponse> response = new ApiResponse<>(createAccountResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccounts(
            @RequestParam(required = false) UUID customerId
    ) {
        List<AccountResponse> accountResponses = accountService.getAccounts(customerId);
        ApiResponse<List<AccountResponse>> response = new ApiResponse<>(accountResponses);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable Long accountNumber) {
        AccountResponse accountResponse = accountService.getAccount(accountNumber);
        ApiResponse<AccountResponse> response = new ApiResponse<>(accountResponse);
        return ResponseEntity.ok(response);
    }
}
