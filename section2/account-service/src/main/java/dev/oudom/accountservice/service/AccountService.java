package dev.oudom.accountservice.service;

import dev.oudom.accountservice.dto.CustomerDto;

public interface AccountService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

}
