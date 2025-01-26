package com.mequi.config.context;

import io.javalin.http.Context;

public class UserContextServiceImpl implements UserContextService {
  @Override
  public UserContext build(Context context) {
//    return UserContext.builder().build();
    return new UserContext(context.path(), context);
  }
}
