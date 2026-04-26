package dev.oudom.customer.mapper;

import dev.oudom.customer.domain.Customer;
import dev.oudom.customer.dto.CreateCustomerRequest;
import dev.oudom.customer.dto.CreateCustomerResponse;
import dev.oudom.customer.dto.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Customer createCustomerRequestToCustomer(CreateCustomerRequest createCustomerRequest);

    CreateCustomerResponse customerToCreateCustomerResponse(Customer customer);

    CustomerResponse customerToCustomerResponse(Customer customer);
}
