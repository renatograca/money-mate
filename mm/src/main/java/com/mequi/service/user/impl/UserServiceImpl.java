package com.mequi.service.user.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mequi.config.context.UserContext;
import com.mequi.mapper.UserMapper;
import com.mequi.repository.user.UserRepository;
import com.mequi.service.user.UserService;
import com.mequi.service.user.dto.UserData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class UserServiceImpl implements UserService {

  private final ObjectMapper mapper;
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  @Override
  public void create(UserContext context) {
    try {
      final var userData = mapper.readValue(context.context().body(), UserData.class);
      final var userEntity = userMapper.toUserEntity(userData);
      userRepository.create(userEntity);
    } catch (JsonProcessingException e) {
      System.out.println("Error when read value json");
      log.error("Error when read value json: {}", e.getMessage());
    }
  }
}
