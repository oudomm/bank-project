package dev.oudom.bank.customer.service;

import dev.oudom.bank.customer.dto.CreateCustomerRequest;
import dev.oudom.bank.customer.dto.CustomerResponse;
import dev.oudom.bank.customer.dto.UpdateCustomerRequest;

import java.util.UUID;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    CustomerResponse findById(UUID id);

    CustomerResponse findByEmail(String email);

    CustomerResponse update(UUID id, UpdateCustomerRequest request);

    void delete(UUID id);
}
