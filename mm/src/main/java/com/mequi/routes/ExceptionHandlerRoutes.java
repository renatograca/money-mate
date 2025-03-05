package com.mequi.routes;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mequi.exceptions.dto.ErrorResponse;
import com.mequi.exceptions.dto.InvalidPasswordException;
import com.mequi.exceptions.dto.UserNotFoundException;
import io.javalin.Javalin;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class ExceptionHandlerRoutes implements Routers {

  @Override
  public void addRoutes(Javalin server) {
    server.exception(UserNotFoundException.class, (e, context) -> {
      context.status(HttpStatus.BAD_REQUEST);
      context.json(ErrorResponse.builder().message(e.getMessage()).build());
    });

    server.exception(InvalidPasswordException.class, (e, context) -> {
      context.status(HttpStatus.UNAUTHORIZED);
      context.json(ErrorResponse.builder().message(e.getMessage()).build());
    });

    server.exception(Exception.class, (e, context) -> {
      context.status(HttpStatus.INTERNAL_SERVER_ERROR);
      context.json(ErrorResponse.builder().message("An unexpected error occurred").build());
    });
  }
}
