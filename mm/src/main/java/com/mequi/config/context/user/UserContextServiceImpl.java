package com.mequi.config.context.user;

import io.javalin.http.Context;

public class UserContextServiceImpl implements UserContextService {
  @Override
  public UserContext apply(Context context) {
    return UserContext.builder()
        .Path(context.path())
        .context(context)
        .build();
  }
}
