package com.mequi.config.context.user;

import io.javalin.http.Context;
import lombok.Builder;

@Builder
public record UserContext(
    String Path,
    Context context
) {}
