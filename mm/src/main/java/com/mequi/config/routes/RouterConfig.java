package com.mequi.config.routes;

import com.google.inject.Inject;
import com.mequi.routes.Routers;
import com.mequi.routes.UserRoutes;
import io.javalin.Javalin;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class RouterConfig {
  private final List<Routers> routers;

  public void addRouters(Javalin server) {
    routers.forEach(config -> config.addRoutes(server));
  }
}
