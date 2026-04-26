package dev.oudom.customer.service.impl;

import dev.oudom.customer.domain.Customer;
import dev.oudom.customer.dto.CreateCustomerRequest;
import dev.oudom.customer.dto.CreateCustomerResponse;
import dev.oudom.customer.dto.CustomerResponse;
import dev.oudom.customer.dto.UpdateCustomerRequest;
import dev.oudom.customer.exception.CustomerAlreadyExistException;
import dev.oudom.customer.exception.ResourceNotFoundException;
import dev.oudom.customer.mapper.CustomerMapper;
import dev.oudom.customer.repository.CustomerRepository;
import dev.oudom.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.customerToCreateCustomerResponse(savedCustomer);
    }

    @Override
    public CustomerResponse getCustomer(UUID customerId) {
        Customer customer = findCustomer(customerId);
        return customerMapper.customerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "phoneNumber", phoneNumber));
        return customerMapper.customerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(UUID customerId, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = findCustomer(customerId);
        validateUniqueEmail(updateCustomerRequest.email(), customerId);
        validateUniquePhoneNumber(updateCustomerRequest.phoneNumber(), customerId);

        customer.setName(updateCustomerRequest.name());
        customer.setEmail(updateCustomerRequest.email());
        customer.setPhoneNumber(updateCustomerRequest.phoneNumber());

        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerResponse(savedCustomer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        Customer customer = findCustomer(customerId);
        customerRepository.delete(customer);
    }

    private Customer findCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId.toString()));
    }

    private void validateUniqueEmail(String email, UUID currentCustomerId) {
        customerRepository.findByEmail(email)
                .filter(customer -> !customer.getId().equals(currentCustomerId))
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistException("Customer already registered with given email");
                });
    }

    private void validateUniquePhoneNumber(String phoneNumber, UUID currentCustomerId) {
        customerRepository.findByPhoneNumber(phoneNumber)
                .filter(customer -> !customer.getId().equals(currentCustomerId))
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistException("Customer already registered with given phone number");
                });
    }
}
