package com.mequi.routes;


import com.google.inject.Inject;
import com.mequi.controller.UserController;
import io.javalin.Javalin;
import lombok.RequiredArgsConstructor;

public class UserRoutes implements Routers {
  private static final String ROOT_PATH = "/users";
  private static final String GET_USER_PATH = ROOT_PATH + "/{user_id}";
  @Inject
  private UserController userController = new UserController();

  @Override
  public void addRoutes(Javalin server) {
    server.get(GET_USER_PATH, userController::getUser);
    server.post(ROOT_PATH, userController::createUser);
  }
}
