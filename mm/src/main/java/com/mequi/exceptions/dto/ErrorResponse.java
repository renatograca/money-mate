package com.mequi.exceptions.dto;

import lombok.Builder;

@Builder
public record ErrorResponse(
    String message
) {
}
