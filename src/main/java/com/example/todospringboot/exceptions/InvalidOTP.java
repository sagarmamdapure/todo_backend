package com.example.todospringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid OTP")
public class InvalidOTP extends RuntimeException {
  private static final long serialVersionUID = 100L;
}
