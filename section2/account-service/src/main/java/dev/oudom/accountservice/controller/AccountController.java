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

    @PutMapping
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountConstants.MESSAGE_500, AccountConstants.MESSAGE_500));
        }
    }
}
