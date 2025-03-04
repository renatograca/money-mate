package com.mequi.controller;


import com.google.inject.Inject;
import com.mequi.config.context.user.UserContextService;


import com.mequi.service.user.UserService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UserController implements Controller {

  private final UserContextService userContextService;
  private final UserService service;


  public void getUser(Context context) {
    final var id = Long.valueOf(context.pathParam("user_id"));
    final var userOptional = service.findById(id);
    if (userOptional.isEmpty()) {
      context.status(HttpStatus.NOT_FOUND);
      context.json("Not Found");
    } else {
      context.status(HttpStatus.OK);
      context.json(userOptional.get());
    }
  }

  public void createUser(Context context) {
    final var userContext = userContextService.apply(context);
    service.create(userContext);
    context.status(HttpStatus.CREATED);
    context.json("Usuario criado com sucesso.");
  }
}
