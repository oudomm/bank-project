package dev.oudom.customer.mapper;

import dev.oudom.customer.domain.Customer;
import dev.oudom.customer.dto.CreateCustomerRequest;
import dev.oudom.customer.dto.CreateCustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer createCustomerRequestToCustomer(CreateCustomerRequest createCustomerRequest);

    CreateCustomerResponse customerToCreateCustomerResponse(Customer customer);
}
