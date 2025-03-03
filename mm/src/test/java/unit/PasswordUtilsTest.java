package unit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mequi.utils.PasswordUtils;
import org.junit.jupiter.api.Test;

public class PasswordUtilsTest {

  @Test
  void testHash() throws Exception {
    // Arrange
    final var password = "mySecurePassword123";

    // Act
    final var hashedPassword = PasswordUtils.hash(password);

    // Assert
    assertNotNull(hashedPassword);
    assertTrue(hashedPassword.startsWith("$2a$")); // Verifica se o hash é um BCrypt válido
  }

  @Test
  void testVerifyPasswordCorrect() throws Exception {
    // Arrange
    final var password = "mySecurePassword123";
    final var hashedPassword = PasswordUtils.hash(password);

    // Act
    boolean isPasswordCorrect = PasswordUtils.verifyPassword(password, hashedPassword);

    // Assert
    assertTrue(isPasswordCorrect);
  }

  @Test
  void testVerifyPasswordIncorrect() throws Exception {
    // Arrange
    final var correctPassword = "mySecurePassword123";
    final var incorrectPassword = "wrongPassword";
    final var hashedPassword = PasswordUtils.hash(correctPassword);

    // Act
    boolean isPasswordCorrect = PasswordUtils.verifyPassword(incorrectPassword, hashedPassword);

    // Assert
    assertFalse(isPasswordCorrect);
  }

  @Test
  void testVerifyPasswordWithNullInput() throws Exception {
    // Arrange
    final var password = "mySecurePassword123";
    final var hashedPassword = PasswordUtils.hash(password);

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> PasswordUtils.verifyPassword(null, hashedPassword));
    assertThrows(IllegalArgumentException.class, () -> PasswordUtils.verifyPassword(password, null));
    assertThrows(IllegalArgumentException.class, () -> PasswordUtils.verifyPassword(null, null));
  }

  @Test
  void testHashWithNullInput() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> PasswordUtils.hash(null));
  }
}