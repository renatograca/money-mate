package com.mequi.config.context.auth.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mequi.config.context.auth.AuthContextService;
import com.mequi.config.context.auth.dto.AuthContext;
import com.mequi.mapper.UserMapper;
import com.mequi.service.auth.dto.UserAuthRequest;
import io.javalin.http.Context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class AuthContextServiceImpl implements AuthContextService {

  private final ObjectMapper mapper;
  private final UserMapper userMapper;

  @Override
  public AuthContext apply(Context context) {

    try {
      final var userAuthRequest = mapper.readValue(context.body(), UserAuthRequest.class);
      final var userAuthData = userMapper.toUserAuthData(userAuthRequest);

      return AuthContext.builder()
          .path(context.path())
          .userId(userAuthData.id())
          .user(userAuthData)
          .build();
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Fail Auth");
    }
  }
}
