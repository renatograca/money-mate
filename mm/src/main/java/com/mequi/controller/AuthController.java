package com.mequi.controller;

import com.google.inject.Inject;
import com.mequi.config.context.auth.AuthContextService;
import com.mequi.service.auth.AuthService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class AuthController implements Controller {

  private final AuthContextService authContextService;
  private final AuthService authService;

  public void login(Context context) {
    final var authContext = authContextService.apply(context);



    context.status(HttpStatus.OK);
    context.json("Success Authentication");
  }
}
