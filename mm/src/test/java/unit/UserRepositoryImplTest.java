package unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mequi.exceptions.UserNotFoundException;
import com.mequi.repository.user.entity.UserEntity;
import com.mequi.repository.user.impl.UserRepositoryImpl;
import com.mequi.service.user.dto.StatusAccount;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryImplTest {

  @Mock
  private DataSource dataSource;

  @Mock
  private Connection connection;

  @Mock
  private PreparedStatement preparedStatement;

  @Mock
  private ResultSet resultSet;

  @InjectMocks
  private UserRepositoryImpl userRepository;

  @BeforeEach
  void setUp() throws SQLException {
    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
  }

  @Test
  void testFindById() throws SQLException, UserNotFoundException {
    // Arrange
    final var userId = 1L;
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getLong("id")).thenReturn(userId);
    when(resultSet.getString("full_name")).thenReturn("John Doe");
    when(resultSet.getString("password_hash")).thenReturn("hashPassword");
    when(resultSet.getString("email")).thenReturn("john.doe@example.com");
    when(resultSet.getDate("date_of_birth")).thenReturn(java.sql.Date.valueOf("1990-01-01"));
    when(resultSet.getLong("phone")).thenReturn(1234567890L);
    when(resultSet.getString("account_status")).thenReturn("ACTIVE");

    // Act
    final var user = userRepository.findById(userId);

    // Assert
    assertTrue(user.isPresent());
    assertEquals(userId, user.get().id());
    assertEquals("John Doe", user.get().fullName());
    assertEquals("john.doe@example.com", user.get().email());
    assertEquals(java.sql.Date.valueOf("1990-01-01"), user.get().dateOfBirth());
    assertEquals(1234567890L, user.get().phone());
    assertEquals(StatusAccount.ACTIVE, user.get().accountStatus());
  }

  @Test
  void testFindByIdNotFound() throws SQLException, UserNotFoundException {
    // Arrange
    final var userId = 1L;
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(false);

    // Act
    final var user = userRepository.findById(userId);

    // Assert
    assertFalse(user.isPresent());
  }

  @Test
  void testFindByEmail() throws SQLException {
    // Arrange
    String email = "john.doe@example.com";
    when(preparedStatement.executeQuery()).thenReturn(resultSet);
    when(resultSet.next()).thenReturn(true);
    when(resultSet.getLong("id")).thenReturn(1L);
    when(resultSet.getString("full_name")).thenReturn("John Doe");
    when(resultSet.getString("password_hash")).thenReturn("hashPassword");
    when(resultSet.getString("email")).thenReturn(email);
    when(resultSet.getDate("date_of_birth")).thenReturn(java.sql.Date.valueOf("1990-01-01"));
    when(resultSet.getLong("phone")).thenReturn(1234567890L);
    when(resultSet.getString("account_status")).thenReturn("ACTIVE");

    // Act
    final var user = userRepository.findByEmail(email);

    // Assert
    assertTrue(user.isPresent());
    assertEquals(email, user.get().email());
  }

  @Test
  void testCreateUser() throws SQLException {
    // Arrange
    UserEntity userData = UserEntity.builder()
        .fullName("John Doe")
        .passwordHash("password123")
        .email("john.doe@example.com")
        .dateOfBirth(java.sql.Date.valueOf("1990-01-01"))
        .phone(1234567890L)
        .accountStatus(StatusAccount.ACTIVE)
        .build();

    when(preparedStatement.executeUpdate()).thenReturn(1);

    // Act
    userRepository.create(userData);

    // Assert
    verify(preparedStatement, times(1)).setString(1, "John Doe");
    verify(preparedStatement, times(1)).setString(2, "password123");
    verify(preparedStatement, times(1)).setString(3, "john.doe@example.com");
    verify(preparedStatement, times(1)).setDate(4, java.sql.Date.valueOf("1990-01-01"));
    verify(preparedStatement, times(1)).setLong(5, 1234567890L);
    verify(preparedStatement, times(1)).setString(6, "ACTIVE");
    verify(preparedStatement, times(1)).executeUpdate();
  }

  @Test
  void testCreateUserThrowsException() throws SQLException {
    // Arrange
    final var userData = UserEntity.builder()
        .fullName("John Doe")
        .passwordHash("password123")
        .email("john.doe@example.com")
        .dateOfBirth(java.sql.Date.valueOf("1990-01-01"))
        .phone(1234567890L)
        .accountStatus(StatusAccount.ACTIVE)
        .build();

    when(preparedStatement.executeUpdate()).thenThrow(new SQLException("Database error"));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> userRepository.create(userData));
    assertEquals("Error when create user", exception.getMessage());
  }
}