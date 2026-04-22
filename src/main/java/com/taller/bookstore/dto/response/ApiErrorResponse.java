package com.taller.bookstore.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class ApiErrorResponse {
    private final String status;
    private final int code;
    private final String message;
    private final List<String> errors;
    private final OffsetDateTime timestamp;
    private final String path;
}
