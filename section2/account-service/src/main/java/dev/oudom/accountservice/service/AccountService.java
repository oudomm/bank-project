package dev.oudom.accountservice.service;

import dev.oudom.accountservice.dto.CustomerDto;

public interface AccountService {

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Account Details based on a give mobile number
     */
    CustomerDto fetchAccount(String mobileNumber);

}
