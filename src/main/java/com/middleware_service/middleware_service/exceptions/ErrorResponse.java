package com.middleware_service.middleware_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
