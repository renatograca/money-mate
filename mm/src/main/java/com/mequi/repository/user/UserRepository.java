package com.mequi.repository.user;

import com.mequi.exceptions.dto.UserNotFoundException;
import com.mequi.repository.user.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findById(Long id) throws UserNotFoundException;
  Optional<UserEntity> findByEmail(String email) throws UserNotFoundException;
  void create(UserEntity userData);
}
