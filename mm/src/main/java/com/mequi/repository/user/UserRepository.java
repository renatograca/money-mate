package com.mequi.repository.user;

import com.mequi.repository.user.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findById(Long id);
  void create(UserEntity userData);
}
