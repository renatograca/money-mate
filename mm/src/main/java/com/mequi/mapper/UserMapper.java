package com.mequi.mapper;

import com.mequi.repository.user.entity.UserEntity;
import com.mequi.service.user.dto.UserData;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
  UserEntity toUserEntity(UserData userData);
}
