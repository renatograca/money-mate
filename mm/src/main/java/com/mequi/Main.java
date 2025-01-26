package com.mequi;

import com.google.inject.Guice;
import com.mequi.config.dependency.AppModule;
import com.mequi.config.routes.RouterConfig;
import com.mequi.config.server.JavalinConfig;
import com.mequi.routes.UserRoutes;
import io.javalin.Javalin;


public class Main {


  public static void main(String[] args) {
    final var app = JavalinConfig.create();
    routerConfig(app);
  }

  private static void routerConfig(Javalin app) {
    final var injector = Guice.createInjector(new AppModule());
    final var userRouters = injector.getInstance(UserRoutes.class);
    userRouters.addRoutes(app);
//    new RouterConfig().addRouters(app);
  }
}
