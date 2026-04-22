package com.taller.bookstore.config;

import com.taller.bookstore.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class ApiResponseBuilder {

    public <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(status.value())
                .message(message)
                .data(data)
                .timestamp(OffsetDateTime.now())
                .build();
    }
}
