package com.mequi.routes;

import static com.mequi.config.routes.ResourceConfig.AuthPaths.ROOT_PATH;

import com.google.inject.Inject;
import com.mequi.controller.AuthController;
import io.javalin.Javalin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class AuthRoutes implements Routers {
  private final AuthController authController;

  @Override
  public void addRoutes(Javalin server) {
    server.post(ROOT_PATH, authController::login);
  }
}
