package com.mequi.service.user;

import com.mequi.config.context.UserContext;
import com.mequi.service.user.dto.UserDTO;
import java.util.Optional;

public interface UserService {
  Optional<UserDTO> findById(Long id);
  void create(UserContext context);
}
