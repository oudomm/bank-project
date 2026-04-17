package dev.oudom.account.controller;

import dev.oudom.account.dto.*;
import dev.oudom.account.service.AccountService;
import dev.oudom.account.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCustomerResponse>> createCustomer(
            @Valid @RequestBody CreateCustomerRequest createCustomerRequest
    ) {
        CreateCustomerResponse createCustomerResponse = customerService.createCustomer(createCustomerRequest);
        ApiResponse<CreateCustomerResponse> response = new ApiResponse<>(createCustomerResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{customerId}/accounts")
    public ResponseEntity<ApiResponse<CreateAccountResponse>> createAccount(
            @PathVariable UUID customerId,
            @Valid @RequestBody CreateAccountRequest createAccountRequest
    ) {
        CreateAccountResponse createAccountResponse = accountService.createAccount(customerId, createAccountRequest);
        ApiResponse<CreateAccountResponse> response = new ApiResponse<>(createAccountResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{customerId}/accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAllAccounts(@PathVariable UUID customerId) {
        List<AccountResponse> accountResponses = accountService.getAllAccounts(customerId);
        ApiResponse<List<AccountResponse>> response = new ApiResponse<>(accountResponses);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
