package dev.oudom.bank.account.exception;

public class AccountNotActiveException extends RuntimeException {

    public AccountNotActiveException(String accountNumber) {
        super("Account '%s' is not active".formatted(accountNumber));
    }
}
