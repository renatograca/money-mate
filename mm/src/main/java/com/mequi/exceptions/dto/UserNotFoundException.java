package com.mequi.exceptions.dto;

public class UserNotFoundException extends Exception {
  public UserNotFoundException(String message) {
    super(message);
  }
}
