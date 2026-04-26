package dev.oudom.account.mapper;

import dev.oudom.account.domain.Account;
import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);

    CreateAccountResponse accountToCreateAccountResponse(Account account);

    AccountResponse accountToAccountResponse(Account account);
}
