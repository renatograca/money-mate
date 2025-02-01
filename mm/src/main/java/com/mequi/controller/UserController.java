package com.mequi.controller;


import com.google.inject.Inject;
import com.mequi.config.context.UserContextService;


import com.mequi.service.user.UserService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UserController implements Controller{

  private final UserContextService userContextService;
  private final UserService service;


  public void getUser(Context context) {
    context.json("Hello World!").status(HttpStatus.OK);
  }

  public void createUser(Context context) {
    final var userContext = userContextService.build(context);
    service.create(userContext);
    context.json("Usuario criado com sucesso.").status(HttpStatus.CREATED);
  }
}
