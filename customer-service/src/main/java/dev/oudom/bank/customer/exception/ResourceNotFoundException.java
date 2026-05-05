package dev.oudom.bank.customer.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, String value) {
        super("%s not found with %s: '%s'".formatted(resource, field, value));
    }
}
