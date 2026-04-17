package dev.oudom.account.service.impl;

import dev.oudom.account.domain.Account;
import dev.oudom.account.domain.Customer;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import dev.oudom.account.mapper.AccountMapper;
import dev.oudom.account.repository.AccountRepository;
import dev.oudom.account.repository.CustomerRepository;
import dev.oudom.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static final long MIN_ACCOUNT_NUMBER = 100_000_000L;
    private static final long MAX_ACCOUNT_NUMBER = 1_000_000_000L;

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;

    @Override
    public CreateAccountResponse createAccount(UUID customerId, CreateAccountRequest createAccountRequest) {
        log.info("customerId:{}, createAccountRequest:{}", customerId, createAccountRequest);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer does not exist"));
        Account account = accountMapper.createAccountRequestToAccount(createAccountRequest);
        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("System");
        Account savedAccount = accountRepository.save(account);
        return accountMapper.accountToCreateAccountResponse(savedAccount);
    }

    private Long generateAccountNumber() {
        long accountNumber;
        do {
            accountNumber = ThreadLocalRandom.current().nextLong(MIN_ACCOUNT_NUMBER, MAX_ACCOUNT_NUMBER);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
