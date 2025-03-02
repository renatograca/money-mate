package com.mequi.repository.user.impl;

import com.google.inject.Inject;
import com.mequi.repository.user.UserRepository;
import com.mequi.repository.user.entity.UserEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class UserRepositoryImpl implements UserRepository {
  private static final String FULL_NAME = "full_name";
  private static final String PASSWORD = "password_hash";
  private static final String EMAIL = "email";
  private static final String DATE_OF_BIRTH = "date_of_birth";
  private static final String PHONE = "phone";
  private static final String ACCOUNT_STATUS = "account_status";
  private final DataSource dataSource;

  @Override
  public Optional<UserEntity> findById(Long id) {
    final var data = "("
        + buildDataString(FULL_NAME, EMAIL, DATE_OF_BIRTH, PHONE, ACCOUNT_STATUS)
        + ")";
    final var query = "SELECT " + data + " FROM users WHERE id = ?";
    try(
        final var conn = dataSource.getConnection();
        final var stmt = conn.prepareStatement(query)
    ) {
      stmt.setLong(1, id);
      final var user = stmt.executeQuery();
    } catch (SQLException e) {
      log.error("Error ao encontrar o usuário {}", id);
      throw new RuntimeException("Error find user");
    }
    return Optional.empty();
  }

  @Override
  public void create(UserEntity userData) {
    final var data = "("
        + buildDataString(FULL_NAME, PASSWORD, EMAIL, DATE_OF_BIRTH, PHONE, ACCOUNT_STATUS)
        + ")";
    final var query = "INSERT INTO users " + data + " VALUES (?, ?, ?, ?, ?, ?)";
    try(
        final var conn = dataSource.getConnection();
        final var stmt = conn.prepareStatement(query)
    ) {
      stmt.setString(1, userData.fullName());
      stmt.setString(2, userData.password());
      stmt.setString(3, userData.email());
      stmt.setDate(4, userData.dateOfBirth());
      stmt.setLong(5, userData.phone());
      stmt.setString(6, userData.accountStatus().name());
      stmt.executeUpdate();
    } catch (SQLException e) {
      log.error("Erro ao criar usuario");
      throw new RuntimeException("Error when create user");
    }
  }

  private UserEntity mapUserEntity(ResultSet r) throws SQLException {
    return UserEntity.builder()
        .fullName(r.getString("status"))

        .build();
  }

  private String buildDataString(String ...strings) {
    return String.join(", ", strings);
  }
}
