package dev.oudom.account.service.impl;

import dev.oudom.account.domain.Account;
import dev.oudom.account.domain.Customer;
import dev.oudom.account.exception.CustomerAlreadyExistException;
import dev.oudom.account.mapper.AccountMapper;
import dev.oudom.account.dto.AccountDto;
import dev.oudom.account.dto.CustomerDto;
import dev.oudom.account.constant.AccountConstant;
import dev.oudom.account.mapper.CustomerMapper;
import dev.oudom.account.repository.AccountRepository;
import dev.oudom.account.repository.CustomerRepository;
import dev.oudom.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;
    private final CustomerMapper customerMapper;

    @Override
    public AccountDto createAccount(CustomerDto customerDto) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDto);
        if (customerRepository.existsByMobileNumber(customerDto.mobileNumber())) {
            throw new CustomerAlreadyExistException("Customer already registered with given mobile number");
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Admin");
        Customer savedCustomer = customerRepository.save(customer);
        Account newAccount = createNewAccount(savedCustomer);
        Account savedAccount = accountRepository.save(newAccount);
        return accountMapper.accountToAccountDto(savedAccount);
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setAccountType(AccountConstant.SAVINGS);
        newAccount.setBranchAddress(AccountConstant.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Admin");
        return newAccount;
    }

    private Long generateAccountNumber() {
        long accountNumber;
        do {
            accountNumber = ThreadLocalRandom
                    .current().nextLong(AccountConstant.MIN_ACCOUNT_NUMBER, AccountConstant.MAX_ACCOUNT_NUMBER);
        } while (accountRepository.existsById(accountNumber));
        return accountNumber;
    }
}
