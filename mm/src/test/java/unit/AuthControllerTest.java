package unit;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mequi.config.context.auth.AuthContextService;
import com.mequi.config.context.auth.dto.AuthContext;
import com.mequi.controller.AuthController;
import com.mequi.controller.dto.AuthResponse;
import com.mequi.exceptions.InvalidPasswordException;
import com.mequi.exceptions.UserNotFoundException;
import com.mequi.repository.user.entity.UserEntity;
import com.mequi.service.auth.AuthService;
import com.mequi.service.auth.dto.UserAuthData;
import com.mequi.service.user.UserService;
import com.mequi.utils.PasswordUtils;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthControllerTest {

  @Mock
  private AuthContextService authContextService;

  @Mock
  private AuthService authService;

  @Mock
  private UserService userService;

  @Mock
  private Context context;

  @InjectMocks
  private AuthController authController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testLogin_Success() throws UserNotFoundException, InvalidPasswordException {
    // Arrange
    final var password = "password";
    final var authContext = AuthContext.builder().userId(1L).user(UserAuthData.builder().password(password).build()).build();
    final var userEntity = UserEntity.builder().passwordHash(PasswordUtils.hash(password)).build();
    final var token = "generatedToken";

    when(authContextService.apply(context)).thenReturn(authContext);
    when(userService.getUserEntityById(anyLong())).thenReturn(Optional.of(userEntity));
    when(authService.generateToken(1L)).thenReturn(token);

    // Act
    authController.login(context);

    // Assert
    verify(context).status(HttpStatus.OK);
    verify(context).json(argThat(response -> {
      final var authResponse = (AuthResponse) response;
      return "Authentication successful".equals(authResponse.message()) &&
          token.equals(authResponse.token());
    }));
  }

  @Test
  void testLogin_UserNotFound() throws UserNotFoundException {
    // Arrange
    final var authContext = AuthContext.builder().user(UserAuthData.builder().password("password").build()).build();

    when(authContextService.apply(context)).thenReturn(authContext);
    when(userService.getUserEntityById(1L)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> authController.login(context));
  }

  @Test
  void testLogin_InvalidPassword() throws UserNotFoundException {
    // Arrange
    final var correctPassword = "correctPassword";
    final var wrongPassword = "wrongPassword";
    final var authContext = AuthContext.builder().userId(1L).user(UserAuthData.builder().password(wrongPassword).build()).build();
    final var userEntity = UserEntity.builder().passwordHash(PasswordUtils.hash(correctPassword)).build();

    when(authContextService.apply(context)).thenReturn(authContext);
    when(userService.getUserEntityById(anyLong())).thenReturn(Optional.of(userEntity));

    // Act & Assert
    assertThrows(InvalidPasswordException.class, () -> authController.login(context));
  }
}