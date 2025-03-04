package com.mequi.service.auth.dto;

public record UserAuthData(
    long id,
    String email,
    String passwordHash
) {
}
