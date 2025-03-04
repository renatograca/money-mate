package com.mequi.config.context.user;

import io.javalin.http.Context;

public interface UserContextService {
  UserContext build(Context context);
}
