package dev.oudom.account.service;

import dev.oudom.account.dto.AccountDto;
import dev.oudom.account.dto.CustomerDto;

public interface AccountService {
    AccountDto createAccount(CustomerDto customerDto);
}
