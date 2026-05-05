package dev.oudom.bank.account.mapper;

import dev.oudom.bank.account.domain.Account;
import dev.oudom.bank.account.dto.AccountResponse;
import dev.oudom.bank.account.dto.CreateAccountRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "balance", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "status", constant = "ACTIVE")
    Account toEntity(CreateAccountRequest request, String accountNumber);

    AccountResponse toResponse(Account account);
}
