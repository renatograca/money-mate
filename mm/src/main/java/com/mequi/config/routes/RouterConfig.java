package com.mequi.config.routes;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mequi.routes.AuthRoutes;
import com.mequi.routes.Routers;
import com.mequi.routes.UserRoutes;
import io.javalin.Javalin;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class RouterConfig {

  private final Injector injector;

  public void configureRouters(Javalin app) {
    final var userRouters = injector.getInstance(UserRoutes.class);
    final var authRouters = injector.getInstance(AuthRoutes.class);
    final var routes = List.of(userRouters, authRouters);
    routes.forEach(router -> router.addRoutes(app));
  }
}
