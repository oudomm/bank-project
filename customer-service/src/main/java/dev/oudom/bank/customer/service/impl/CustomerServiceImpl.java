package dev.oudom.bank.customer.service.impl;

import dev.oudom.bank.customer.domain.Customer;
import dev.oudom.bank.customer.dto.CreateCustomerRequest;
import dev.oudom.bank.customer.dto.CustomerResponse;
import dev.oudom.bank.customer.dto.UpdateCustomerRequest;
import dev.oudom.bank.customer.exception.DuplicateResourceException;
import dev.oudom.bank.customer.exception.ResourceNotFoundException;
import dev.oudom.bank.customer.mapper.CustomerMapper;
import dev.oudom.bank.customer.repository.CustomerRepository;
import dev.oudom.bank.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Customer", "email", request.email());
        }
        Customer customer = customerRepository.save(customerMapper.toEntity(request));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        return customerMapper.toResponse(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse findByEmail(String email) {
        return customerRepository.findByEmail(email)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));
    }

    @Override
    @Transactional
    public CustomerResponse update(UUID id, UpdateCustomerRequest request) {
        Customer customer = getOrThrow(id);
        customerMapper.updateEntity(customer, request);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        customerRepository.delete(getOrThrow(id));
    }

    private Customer getOrThrow(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id.toString()));
    }
}
