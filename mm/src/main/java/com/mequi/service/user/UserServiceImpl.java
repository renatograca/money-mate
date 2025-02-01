package com.mequi.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mequi.config.context.UserContext;
import com.mequi.service.user.dto.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UserServiceImpl implements UserService {

  private final ObjectMapper mapper;

  @Override
  public void create(UserContext context) {
    try {
      final var newUser = mapper.readValue(context.context().body(), UserData.class);
    } catch (JsonProcessingException e) {
      System.out.println("Error when read value json");
      log.error("Error when read value json: {}", e.getMessage());
    }
  }
}
