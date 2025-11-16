package dev.oudom.accountservice.service;

import dev.oudom.accountservice.constant.AccountConstants;
import dev.oudom.accountservice.domain.Account;
import dev.oudom.accountservice.domain.Customer;
import dev.oudom.accountservice.dto.AccountDto;
import dev.oudom.accountservice.dto.CustomerDto;
import dev.oudom.accountservice.exception.CustomerAlreadyExistsException;
import dev.oudom.accountservice.exception.ResourceNotFoundException;
import dev.oudom.accountservice.mapper.AccountMapper;
import dev.oudom.accountservice.mapper.CustomerMapper;
import dev.oudom.accountservice.repository.AccountRepository;
import dev.oudom.accountservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobile number: " + customerDto.getMobileNumber());
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");

        Customer savedCustomer = customerRepository.save(customer);

        accountRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Account account = accountRepository.findByCustomerId(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }

    /**
     *
     * @param customerDto - Customer Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;

        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {

            Account account = accountRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Account",
                            "AccountNumber",
                            accountDto.getAccountNumber().toString()
                    )
            );

            AccountMapper.mapToAccount(accountDto, account);
            account = accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()));

            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }
}
