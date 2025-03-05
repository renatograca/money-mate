package unit;

import com.mequi.exceptions.ApiException;
import com.mequi.exceptions.UserNotFoundException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mequi.config.context.user.UserContext;
import com.mequi.mapper.UserMapper;
import com.mequi.repository.user.UserRepository;
import com.mequi.repository.user.entity.UserEntity;
import com.mequi.service.user.dto.StatusAccount;
import com.mequi.service.user.dto.UserDTO;
import com.mequi.service.user.dto.UserData;
import com.mequi.service.user.impl.UserServiceImpl;
import io.javalin.http.Context;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

  @Mock
  private ObjectMapper mapper;

  @Mock
  private UserMapper userMapper;

  @Mock
  private UserRepository userRepository;

  @Mock
  private Context context;

  @InjectMocks
  private UserServiceImpl userService;

  private UserEntity userEntity;
  private UserDTO userDTO;
  private UserData userData;
  private UserContext userContext;

  @BeforeEach
  void setUp() {
    // Configuração comum para os testes
    userEntity = UserEntity.builder()
        .id(1L)
        .fullName("John Doe")
        .email("john.doe@example.com")
        .dateOfBirth(java.sql.Date.valueOf("1990-01-01"))
        .phone(1234567890L)
        .accountStatus(StatusAccount.ACTIVE)
        .build();

    userDTO = UserDTO.builder()
        .fullName("John Doe")
        .email("john.doe@example.com")
        .dateOfBirth(java.sql.Date.valueOf("1990-01-01"))
        .phone(1234567890L)
        .accountStatus(StatusAccount.ACTIVE)
        .build();

    userData = UserData.builder()
        .fullName("John Doe")
        .email("john.doe@example.com")
        .password("password123")
        .dateOfBirth(java.sql.Date.valueOf("1990-01-01"))
        .phone(1234567890L)
        .accountStatus(StatusAccount.ACTIVE)
        .build();

    userContext = new UserContext("/users", context);
  }

  @Test
  void testFindById() throws UserNotFoundException {
    // Arrange
    when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
    when(userMapper.toUserDTO(userEntity)).thenReturn(userDTO);

    // Act
    final var result = userService.findById(1L);

    // Assert
    verify(userRepository, times(1)).findById(1L);
    verify(userMapper, times(1)).toUserDTO(userEntity);
    assertTrue(result.isPresent());
    assertEquals(userDTO, result.get());
  }

  @Test
  void testFindByIdNotFound() throws UserNotFoundException {
    // Arrange
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    // Act
    final var result = userService.findById(1L);

    // Assert
    verify(userRepository, times(1)).findById(1L);
    verify(userMapper, never()).toUserDTO(any());
    assertFalse(result.isPresent());
  }

  @Test
  void testCreateUser() throws JsonProcessingException, SQLException, ApiException {
    // Arrange
    when(mapper.readValue(userContext.context().body(), UserData.class)).thenReturn(userData);
    when(userRepository.findByEmail(userData.email())).thenReturn(Optional.empty());
    when(userMapper.toUserEntity(userData)).thenReturn(userEntity);

    // Act
    userService.create(userContext);

    // Assert
    verify(mapper, times(1)).readValue(userContext.context().body(), UserData.class);
    verify(userRepository, times(1)).findByEmail(userData.email());
    verify(userMapper, times(1)).toUserEntity(userData);
    verify(userRepository, times(1)).create(userEntity);
  }

  @Test
  void testCreateUserThrowsExceptionWhenEmailAlreadyRegistered() throws JsonProcessingException, SQLException {
    // Arrange
    when(mapper.readValue(userContext.context().body(), UserData.class)).thenReturn(userData);
    when(userRepository.findByEmail(userData.email())).thenReturn(Optional.of(userEntity));

    // Act & Assert
    RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.create(userContext));

    verify(mapper, times(1)).readValue(userContext.context().body(), UserData.class);
    verify(userRepository, times(1)).findByEmail(userData.email());
    verify(userMapper, never()).toUserEntity(any());
    verify(userRepository, never()).create(any());
    assertEquals("email already registered.", exception.getMessage());
  }

  @Test
  void testCreateUserThrowsExceptionWhenJsonProcessingFails() throws JsonProcessingException, SQLException {
    // Arrange
    when(mapper.readValue(userContext.context().body(), UserData.class))
        .thenThrow(new JsonProcessingException("Invalid JSON") {});

    // Act & Assert
    assertDoesNotThrow(() -> userService.create(userContext));

    verify(mapper, times(1)).readValue(userContext.context().body(), UserData.class);
    verify(userRepository, never()).findByEmail(any());
    verify(userMapper, never()).toUserEntity(any());
    verify(userRepository, never()).create(any());
  }


  @Test
  void testGetUserEntityById() throws UserNotFoundException {
    // Arrange
    when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

    // Act
    final var result = userService.getUserEntityById(1L);

    // Assert
    verify(userRepository, times(1)).findById(1L);
    assertTrue(result.isPresent());
    assertEquals(userEntity, result.get());
  }

  @Test
  void testGetUserEntityByIdNotFound() throws UserNotFoundException {
    // Arrange
    when(userRepository.findById(1L)).thenReturn(Optional.empty());

    // Act
    final var result = userService.getUserEntityById(1L);

    // Assert
    verify(userRepository, times(1)).findById(1L);
    assertFalse(result.isPresent());
  }
}