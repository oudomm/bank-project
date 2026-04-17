package dev.oudom.account.mapper;

import dev.oudom.account.domain.Customer;
import dev.oudom.account.dto.CreateCustomerRequest;
import dev.oudom.account.dto.CreateCustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer createCustomerRequestToCustomer(CreateCustomerRequest createCustomerRequest);

    CreateCustomerResponse customerToCreateCustomerResponse(Customer customer);
}
