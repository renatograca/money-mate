package com.mequi.config.dependency;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.mequi.config.context.user.UserContextService;
import com.mequi.config.context.user.UserContextServiceImpl;
import com.mequi.config.data_base.connection.DataBaseConfig;
import com.mequi.mapper.UserMapper;
import com.mequi.mapper.UserMapperImpl;
import com.mequi.repository.user.UserRepository;
import com.mequi.repository.user.impl.UserRepositoryImpl;
import com.mequi.routes.Routers;
import com.mequi.routes.UserRoutes;
import com.mequi.service.user.UserService;
import com.mequi.service.user.impl.UserServiceImpl;
import javax.sql.DataSource;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    userModule();
    dataBaseConfig();
  }

  private void userModule() {
    bind(UserMapper.class).to(UserMapperImpl.class);
    bind(UserContextService.class).to(UserContextServiceImpl.class);
    Multibinder.newSetBinder(binder(), Routers.class)
            .addBinding().to(UserRoutes.class);
    bind(UserService.class).to(UserServiceImpl.class);
  }

  private void dataBaseConfig() {
    bind(DataSource.class).toInstance(DataBaseConfig.getDataSource());
    bind(UserRepository.class).to(UserRepositoryImpl.class);
  }
}
