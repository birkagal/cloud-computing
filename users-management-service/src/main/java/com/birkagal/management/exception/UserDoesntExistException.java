package com.birkagal.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserDoesntExistException extends RuntimeException {
    private static final long serialVersionUID = -2095793919029767140L;

    public UserDoesntExistException() {
    }

    public UserDoesntExistException(String message) {
        super(message);
    }

    public UserDoesntExistException(Throwable cause) {
        super(cause);
    }

    public UserDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
