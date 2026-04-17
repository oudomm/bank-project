package dev.oudom.account.controller;

import dev.oudom.account.constant.AccountConstant;
import dev.oudom.account.dto.AccountDto;
import dev.oudom.account.dto.ApiResponse;
import dev.oudom.account.dto.CustomerDto;
import dev.oudom.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ApiResponse<AccountDto>> createAccount(@Valid @RequestBody CustomerDto customerDto) {
         AccountDto accountDto = accountService.createAccount(customerDto);
        ApiResponse<AccountDto> response = new ApiResponse<>(
                AccountConstant.STATUS_201,
                AccountConstant.MESSAGE_201,
                accountDto
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
