package com.mequi.service.auth.dto;

public record UserAuthRequest(
    long id,
    String email,
    String password
) {
}
