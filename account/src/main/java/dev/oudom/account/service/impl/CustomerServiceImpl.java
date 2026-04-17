package dev.oudom.account.service.impl;

import dev.oudom.account.domain.Customer;
import dev.oudom.account.dto.CreateCustomerRequest;
import dev.oudom.account.dto.CreateCustomerResponse;
import dev.oudom.account.exception.CustomerAlreadyExistException;
import dev.oudom.account.mapper.CustomerMapper;
import dev.oudom.account.repository.CustomerRepository;
import dev.oudom.account.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest) {
        log.info("createCustomerRequest:{}", createCustomerRequest);

        if (customerRepository.existsByEmail(createCustomerRequest.email())) {
            throw new CustomerAlreadyExistException("Customer already registered with given email");
        }

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber())) {
            throw new CustomerAlreadyExistException("Customer already registered with given email");
        }

        Customer customer = customerMapper.createCustomerRequestToCustomer(createCustomerRequest);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("System");
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.customerToCreateCustomerResponse(savedCustomer);
    }
}
