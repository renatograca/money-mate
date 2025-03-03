package com.mequi.service.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Builder;

@Builder
public record UserDTO(
    String fullName,
    String email,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    Date dateOfBirth,
    long phone,
    StatusAccount accountStatus
) {
}
