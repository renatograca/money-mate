package com.mequi.utils;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtils {

  public static String hash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean verifyPassword(String password, String passwordHash) {
    return BCrypt.checkpw(password, passwordHash);
  }
}
