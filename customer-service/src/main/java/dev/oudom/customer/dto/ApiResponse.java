package dev.oudom.customer.dto;

public record ApiResponse<T>(
        T data
) {
}
