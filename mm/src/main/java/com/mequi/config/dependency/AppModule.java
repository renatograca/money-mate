package com.mequi.config.dependency;

import com.google.inject.AbstractModule;
import com.mequi.config.context.UserContextService;
import com.mequi.config.context.UserContextServiceImpl;
import com.mequi.controller.UserController;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    userModule();
  }

  private void userModule() {
    bind(UserContextService.class).to(UserContextServiceImpl.class);
  }
}
