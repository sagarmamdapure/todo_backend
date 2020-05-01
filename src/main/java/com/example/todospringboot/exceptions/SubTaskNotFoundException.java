package com.example.todospringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This SubTask is not found in the system")
public class SubTaskNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 100L;

    public SubTaskNotFoundException(String exception) {
        super(exception);
    }
}