package com.mequi.config.context;

import io.javalin.http.Context;

public interface UserContextService {
  UserContext build(Context context);
}
