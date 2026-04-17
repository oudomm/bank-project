package dev.oudom.account.service;

import dev.oudom.account.dto.CreateCustomerRequest;
import dev.oudom.account.dto.CreateCustomerResponse;

public interface CustomerService {
    CreateCustomerResponse createCustomer(CreateCustomerRequest createCustomerRequest);
}
