package com.example.todospringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This Task is not found in the system")
public class TaskNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 100L;

  public TaskNotFoundException(String exception) {
    super(exception);
  }
}
