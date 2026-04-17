package dev.oudom.account.mapper;

import dev.oudom.account.domain.Account;
import dev.oudom.account.dto.AccountResponse;
import dev.oudom.account.dto.CreateAccountRequest;
import dev.oudom.account.dto.CreateAccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account createAccountRequestToAccount(CreateAccountRequest createAccountRequest);

    CreateAccountResponse accountToCreateAccountResponse(Account account);

    AccountResponse accountToAccountResponse(Account account);
}
