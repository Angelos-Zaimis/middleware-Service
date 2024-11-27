package com.middleware_service.middleware_service.exception;

import org.springframework.dao.DataAccessException;

public class OrderDataAccessException extends DataAccessException {

    public OrderDataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
