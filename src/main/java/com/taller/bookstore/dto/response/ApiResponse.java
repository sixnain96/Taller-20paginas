package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ApiResponse<T> {
    private final String status;
    private final int code;
    private final String message;
    private final T data;
    private final OffsetDateTime timestamp;
}
