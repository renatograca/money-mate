package com.mequi.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mequi.config.context.auth.AuthContextService;
import com.mequi.controller.dto.AuthResponse;
import com.mequi.exceptions.dto.InvalidPasswordException;
import com.mequi.exceptions.dto.UserNotFoundException;
import com.mequi.service.auth.AuthService;
import com.mequi.service.user.UserService;
import com.mequi.utils.PasswordUtils;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class AuthController implements Controller {

  private final AuthContextService authContextService;
  private final AuthService authService;
  private final UserService userService;

  public void login(Context context) throws UserNotFoundException, InvalidPasswordException {
    final var authContext = authContextService.apply(context);
    final var userEntity = userService.getUserEntityById(authContext.userId()).orElse(null);

    if (userEntity == null) {
      throw new UserNotFoundException("User not found");
    }

    if (!PasswordUtils.verifyLogin(authContext, userEntity)) {
      throw new InvalidPasswordException("Invalid password");
    }

    String token = authService.generateToken(authContext.userId());
    sendSuccessResponse(context, token);
  }

  private void sendSuccessResponse(Context context, String token) {
    final var authResponse = AuthResponse.builder()
        .message("Authentication successful")
        .token(token)
        .build();

    context.status(HttpStatus.OK);
    context.json(authResponse);
  }
}
