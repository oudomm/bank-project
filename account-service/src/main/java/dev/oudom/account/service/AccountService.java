package dev.oudom.account.service;

import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest);

    List<AccountResponse> getAccounts(UUID customerId);

    AccountResponse getAccount(Long accountNumber);
}
