package com.mequi.config.dependency;

import com.mequi.routes.Routers;
import com.mequi.routes.UserRoutes;
import java.util.List;

public class DependencyConfig {

  public static List<Routers> getRouters() {
    return List.of(
        new UserRoutes()
    );
  }
}
