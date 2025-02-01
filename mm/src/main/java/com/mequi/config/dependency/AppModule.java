package com.mequi.config.dependency;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.mequi.config.context.UserContextService;
import com.mequi.config.context.UserContextServiceImpl;
import com.mequi.routes.Routers;
import com.mequi.routes.UserRoutes;
import com.mequi.service.user.UserService;
import com.mequi.service.user.UserServiceImpl;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    userModule();
  }

  private void userModule() {
    bind(UserContextService.class).to(UserContextServiceImpl.class);
    Multibinder.newSetBinder(binder(), Routers.class)
            .addBinding().to(UserRoutes.class);
    bind(UserService.class).to(UserServiceImpl.class);
  }
}
