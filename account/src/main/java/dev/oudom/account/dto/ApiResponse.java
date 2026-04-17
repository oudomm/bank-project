package dev.oudom.account.dto;

public record ApiResponse<T>(
        String statusCode,
        String statusMsg,
        T data
) {
}
