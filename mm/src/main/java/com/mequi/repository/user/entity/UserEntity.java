package com.mequi.repository.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mequi.service.user.dto.StatusAccount;
import java.sql.Date;
import lombok.Builder;

@Builder
public record UserEntity(
    Long id,
    String fullName,
    String email,
    String password,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dateOfBirth,
    Long phone,
    StatusAccount accountStatus
) {
}
