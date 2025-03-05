package com.mequi.service.user;

import com.mequi.config.context.user.UserContext;
import com.mequi.exceptions.dto.UserNotFoundException;
import com.mequi.repository.user.entity.UserEntity;
import com.mequi.service.user.dto.UserDTO;
import java.util.Optional;

public interface UserService {
  Optional<UserEntity> getUserEntityById(Long id) throws UserNotFoundException;
  Optional<UserDTO> findById(Long id) throws UserNotFoundException;
  void create(UserContext context);
}
