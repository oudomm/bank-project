package dev.oudom.bank.account.service.impl;

import dev.oudom.bank.account.domain.Account;
import dev.oudom.bank.account.domain.AccountStatus;
import dev.oudom.bank.account.dto.AccountResponse;
import dev.oudom.bank.account.dto.CreateAccountRequest;
import dev.oudom.bank.account.dto.UpdateAccountRequest;
import dev.oudom.bank.account.exception.AccountNotActiveException;
import dev.oudom.bank.account.exception.InsufficientFundsException;
import dev.oudom.bank.account.exception.ResourceNotFoundException;
import dev.oudom.bank.account.mapper.AccountMapper;
import dev.oudom.bank.account.repository.AccountRepository;
import dev.oudom.bank.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Random random = new Random();

    @Override
    @Transactional
    public AccountResponse create(CreateAccountRequest request) {
        String accountNumber = generateUniqueAccountNumber();
        Account account = accountRepository.save(accountMapper.toEntity(request, accountNumber));
        return accountMapper.toResponse(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponse findById(UUID id) {
        return accountMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> findByCustomerId(UUID customerId) {
        return accountRepository.findByCustomerId(customerId)
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public AccountResponse update(UUID id, UpdateAccountRequest request) {
        Account account = getOrThrow(id);
        account.setAccountType(request.accountType());
        account.setStatus(request.status());
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Account account = getOrThrow(id);
        account.setStatus(AccountStatus.CLOSED);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountResponse deposit(UUID id, BigDecimal amount) {
        Account account = getOrThrow(id);
        requireActive(account);
        account.setBalance(account.getBalance().add(amount));
        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponse withdraw(UUID id, BigDecimal amount) {
        Account account = getOrThrow(id);
        requireActive(account);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        }
        account.setBalance(account.getBalance().subtract(amount));
        return accountMapper.toResponse(accountRepository.save(account));
    }

    private Account getOrThrow(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", id.toString()));
    }

    private void requireActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new AccountNotActiveException(account.getAccountNumber());
        }
    }

    private String generateUniqueAccountNumber() {
        String number;
        do {
            number = String.format("%010d", (long) (random.nextDouble() * 10_000_000_000L));
        } while (accountRepository.existsByAccountNumber(number));
        return number;
    }
}
