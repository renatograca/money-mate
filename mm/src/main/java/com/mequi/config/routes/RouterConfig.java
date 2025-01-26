package com.mequi.config.routes;

import com.mequi.routes.Routers;
import com.mequi.routes.UserRoutes;
import io.javalin.Javalin;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RouterConfig {
  private final List<Routers> routers = List.of(
		  new UserRoutes()
		  );

  public void addRouters(Javalin server) {
    routers.forEach(config -> config.addRoutes(server));
  }
}
