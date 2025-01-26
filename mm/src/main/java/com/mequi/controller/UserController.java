package com.mequi.controller;


import com.google.inject.Inject;
import com.mequi.config.context.UserContextService;
import com.mequi.config.context.UserContextServiceImpl;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

public class UserController {
  @Inject
  private UserContextService userContextService;


  public void getUser(Context context) {
    context.json("Hello World!").status(HttpStatus.OK);
  }

  public void createUser(Context context) {
    final var userContext = userContextService.build(context);
    context.json("Usuario criado" + userContext).status(HttpStatus.CREATED);
  }
}
