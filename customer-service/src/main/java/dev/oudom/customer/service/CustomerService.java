package dev.oudom.customer.service;

import dev.oudom.customer.dto.CreateCustomerRequest;
import dev.oudom.customer.dto.CreateCustomerResponse;
import dev.oudom.customer.dto.CustomerResponse;
import dev.oudom.customer.dto.UpdateCustomerRequest;

import java.util.UUID;

public interface CustomerService {
    CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);

    CustomerResponse getCustomer(UUID customerId);

    CustomerResponse getCustomerByPhoneNumber(String phoneNumber);

    CustomerResponse updateCustomer(UUID customerId, UpdateCustomerRequest updateCustomerRequest);

    void deleteCustomer(UUID customerId);
}
