package dev.oudom.bank.account.service;

import dev.oudom.bank.account.dto.AccountResponse;
import dev.oudom.bank.account.dto.CreateAccountRequest;
import dev.oudom.bank.account.dto.UpdateAccountRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountResponse create(CreateAccountRequest request);

    AccountResponse findById(UUID id);

    List<AccountResponse> findByCustomerId(UUID customerId);

    AccountResponse update(UUID id, UpdateAccountRequest request);

    void delete(UUID id);

    AccountResponse deposit(UUID id, BigDecimal amount);

    AccountResponse withdraw(UUID id, BigDecimal amount);
}
