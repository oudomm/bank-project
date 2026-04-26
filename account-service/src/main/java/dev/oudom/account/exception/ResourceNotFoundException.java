package dev.oudom.account.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super("%s not found with %s: %s".formatted(resourceName, fieldName, fieldValue));
    }
}
