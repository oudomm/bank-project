package dev.oudom.account.service;

import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import dev.oudom.account.dto.UpdateAccountRequest;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    List<AccountResponse> getAccounts(UUID customerId);

    AccountResponse getAccount(Long accountNumber);

    AccountResponse updateAccount(Long accountNumber, UpdateAccountRequest updateAccountRequest);

    void deleteAccount(Long accountNumber);
}
