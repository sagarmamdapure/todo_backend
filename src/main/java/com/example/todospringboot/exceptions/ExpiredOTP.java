package com.example.todospringboot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "OTP Expired")
public class ExpiredOTP extends RuntimeException {
  private static final long serialVersionUID = 100L;

  public ExpiredOTP(String exception) {
    super(exception);
  }

  public ExpiredOTP() {
  }
}
