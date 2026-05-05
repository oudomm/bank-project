package dev.oudom.bank.account.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds for this transaction");
    }
}
