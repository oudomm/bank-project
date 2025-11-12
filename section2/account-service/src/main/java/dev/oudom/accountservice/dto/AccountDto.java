package dev.oudom.accountservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto{
    private Long accountNumber;
    private String accountType;
    private String branchAddress;
}
