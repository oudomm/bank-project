package dev.oudom.customer.service.impl;

import dev.oudom.customer.domain.Customer;
import dev.oudom.customer.dto.CreateCustomerRequest;
import dev.oudom.customer.dto.CreateCustomerResponse;
import dev.oudom.customer.exception.CustomerAlreadyExistException;
import dev.oudom.customer.mapper.CustomerMapper;
import dev.oudom.customer.repository.CustomerRepository;
import dev.oudom.customer.service.CustomerService;
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
            throw new CustomerAlreadyExistException("Customer already registered with given phone number");
        }

        Customer customer = customerMapper.createCustomerRequestToCustomer(createCustomerRequest);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("System");
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.customerToCreateCustomerResponse(savedCustomer);
    }
}
