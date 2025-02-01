package com.mequi;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mequi.config.dependency.AppModule;
import com.mequi.config.dependency.ObjectMapperConfig;
import com.mequi.config.server.JavalinConfig;
import com.mequi.routes.UserRoutes;
import io.javalin.Javalin;


public class Main {


  public static void main(String[] args) {
    final var app = JavalinConfig.create();
    final var injector = injectConfig();

    routerConfig(app, injector);
  }

  private static void routerConfig(Javalin app, Injector injector) {
    final var userRouters = injector.getInstance(UserRoutes.class);
    userRouters.addRoutes(app);
  }

  private static Injector injectConfig() {
    return Guice.createInjector(new AppModule(), new ObjectMapperConfig());
  }
}
