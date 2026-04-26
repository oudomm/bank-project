package dev.oudom.account.controller;

import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.ApiResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import dev.oudom.account.dto.ErrorResponseDto;
import dev.oudom.account.dto.UpdateAccountRequest;
import dev.oudom.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
@Tag(
        name = "Account APIs",
        description = "Create, fetch, update, and delete digital banking accounts"
)
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create account", description = "Creates a new digital banking account for an existing customer.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Account created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
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

    @Operation(summary = "List accounts", description = "Returns all accounts, or only accounts for the provided customer id.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Accounts returned")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getAccounts(
            @Parameter(description = "Optional customer id filter")
            @RequestParam(required = false) UUID customerId
    ) {
        List<AccountResponse> accountResponses = accountService.getAccounts(customerId);
        ApiResponse<List<AccountResponse>> response = new ApiResponse<>(accountResponses);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Fetch account", description = "Returns account details for the provided account number.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account returned"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(
            @Parameter(description = "10-digit account number", example = "1234567890")
            @Min(value = 1_000_000_000L, message = "Account number must be 10 digits")
            @Max(value = 9_999_999_999L, message = "Account number must be 10 digits")
            @PathVariable Long accountNumber
    ) {
        AccountResponse accountResponse = accountService.getAccount(accountNumber);
        ApiResponse<AccountResponse> response = new ApiResponse<>(accountResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update account", description = "Updates mutable account details for the provided account number.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Account updated"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccount(
            @Parameter(description = "10-digit account number", example = "1234567890")
            @Min(value = 1_000_000_000L, message = "Account number must be 10 digits")
            @Max(value = 9_999_999_999L, message = "Account number must be 10 digits")
            @PathVariable Long accountNumber,
            @Valid @RequestBody UpdateAccountRequest updateAccountRequest
    ) {
        AccountResponse accountResponse = accountService.updateAccount(accountNumber, updateAccountRequest);
        ApiResponse<AccountResponse> response = new ApiResponse<>(accountResponse);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete account", description = "Deletes an account by account number.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Account deleted"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "10-digit account number", example = "1234567890")
            @Min(value = 1_000_000_000L, message = "Account number must be 10 digits")
            @Max(value = 9_999_999_999L, message = "Account number must be 10 digits")
            @PathVariable Long accountNumber
    ) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.noContent().build();
    }
}
