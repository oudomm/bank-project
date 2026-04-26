package dev.oudom.account.service.impl;

import dev.oudom.account.constants.AccountConstants;
import dev.oudom.account.domain.Account;
import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import dev.oudom.account.dto.UpdateAccountRequest;
import dev.oudom.account.exception.ResourceNotFoundException;
import dev.oudom.account.mapper.AccountMapper;
import dev.oudom.account.repository.AccountRepository;
import dev.oudom.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private static final long MIN_ACCOUNT_NUMBER = 1_000_000_000L;
    private static final long MAX_ACCOUNT_NUMBER = 10_000_000_000L;

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        log.info("createAccountRequest:{}", createAccountRequest);
        Account account = accountMapper.createAccountRequestToAccount(createAccountRequest);
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(resolveDefault(createAccountRequest.accountType(), AccountConstants.DEFAULT_ACCOUNT_TYPE));
        Account savedAccount = accountRepository.save(account);
        return accountMapper.accountToCreateAccountResponse(savedAccount);
    }

    @Override
    public List<AccountResponse> getAccounts(UUID customerId) {
        List<Account> accounts = customerId == null
                ? accountRepository.findAll()
                : accountRepository.findByCustomerId(customerId);
        return accounts.stream().map(accountMapper::accountToAccountResponse).toList();
    }

    @Override
    public AccountResponse getAccount(Long accountNumber) {
        Account account = findAccount(accountNumber);
        return accountMapper.accountToAccountResponse(account);
    }

    @Override
    public AccountResponse updateAccount(Long accountNumber, UpdateAccountRequest updateAccountRequest) {
        Account account = findAccount(accountNumber);
        account.setAccountType(updateAccountRequest.accountType());
        Account savedAccount = accountRepository.save(account);
        return accountMapper.accountToAccountResponse(savedAccount);
    }

    @Override
    public void deleteAccount(Long accountNumber) {
        Account account = findAccount(accountNumber);
        accountRepository.delete(account);
    }

    private Account findAccount(Long accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));
    }

    private Long generateAccountNumber() {
        long accountNumber;
        do {
            accountNumber = ThreadLocalRandom.current().nextLong(MIN_ACCOUNT_NUMBER, MAX_ACCOUNT_NUMBER);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    private String resolveDefault(String value, String defaultValue) {
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
