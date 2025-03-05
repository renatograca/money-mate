package com.mequi.repository.user;

import com.mequi.exceptions.UserNotFoundException;
import com.mequi.repository.user.entity.UserEntity;
import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository {
  Optional<UserEntity> findById(Long id) throws UserNotFoundException;
  Optional<UserEntity> findByEmail(String email);
  void create(UserEntity userData) throws SQLException;
}
