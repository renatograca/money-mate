package com.mequi.config.context;

import io.javalin.http.Context;
import lombok.Builder;

@Builder
public record UserContext(
    String Path,
    Context context
) {}
