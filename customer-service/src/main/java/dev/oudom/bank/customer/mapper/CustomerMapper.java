package dev.oudom.bank.customer.mapper;

import dev.oudom.bank.customer.domain.Customer;
import dev.oudom.bank.customer.dto.CreateCustomerRequest;
import dev.oudom.bank.customer.dto.CustomerResponse;
import dev.oudom.bank.customer.dto.UpdateCustomerRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    Customer toEntity(CreateCustomerRequest request);

    CustomerResponse toResponse(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(@MappingTarget Customer customer, UpdateCustomerRequest request);
}
