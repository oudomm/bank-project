package dev.oudom.accountservice.controller;

import dev.oudom.accountservice.constant.AccountConstants;
import dev.oudom.accountservice.dto.CustomerDto;
import dev.oudom.accountservice.dto.ResponseDto;
import dev.oudom.accountservice.service.AccountService;
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
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {

        accountService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstants.STATUS_201, AccountConstants.MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {

        CustomerDto customerdto = accountService.fetchAccount(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerdto);
    }
}
