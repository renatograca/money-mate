package com.mequi.controller;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mequi.config.context.user.UserContextService;


import com.mequi.exceptions.dto.UserNotFoundException;
import com.mequi.service.user.UserService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UserController implements Controller {

  private final UserContextService userContextService;
  private final UserService service;


  public void getUser(Context context) throws UserNotFoundException {
    final var id = Long.valueOf(context.pathParam("user_id"));
    final var userOptional = service.findById(id);
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException("User not found");
    }
    context.status(HttpStatus.OK);
    context.json(userOptional.get());
  }

  public void createUser(Context context) {
    final var userContext = userContextService.apply(context);
    service.create(userContext);
    context.status(HttpStatus.CREATED);
    context.json("Usuario criado com sucesso.");
  }
}
