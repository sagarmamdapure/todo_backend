package com.example.todospringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "User can't delete or update other user's data")
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 100L;

    public UnauthorizedException(String exception) {
        super(exception);
    }
}
