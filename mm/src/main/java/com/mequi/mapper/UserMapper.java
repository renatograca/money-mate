package com.mequi.mapper;

import com.mequi.repository.user.entity.UserEntity;
import com.mequi.service.auth.dto.UserAuthData;
import com.mequi.service.auth.dto.UserAuthRequest;
import com.mequi.service.user.dto.UserDTO;
import com.mequi.service.user.dto.UserData;
import com.mequi.utils.PasswordUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(imports = PasswordUtils.class)
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", source = "password", qualifiedByName = "hash")
  UserEntity toUserEntity(UserData userData);

  @Mapping(target = "password", ignore = true)
  UserData toUserData(UserEntity user);

  UserDTO toUserDTO(UserEntity user);

  @Mapping(target = "password", source = "password", qualifiedByName = "hash")
  UserAuthData toUserAuthData(UserAuthRequest user);

  @Named("hash")
  default String passwordHash(String password) throws Exception {
    return PasswordUtils.hash(password);
  }
}
