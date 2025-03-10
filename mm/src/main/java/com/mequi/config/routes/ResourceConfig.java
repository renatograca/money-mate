package com.mequi.config.routes;

public class ResourceConfig {
  public static class UserPaths {
    public static final String ROOT_PATH = "/users";
    public static final String USER_ID_PATH = ROOT_PATH + "/{user_id}";
  }
  public static class AuthPaths {
    public static final String ROOT_PATH = "/auth";
  }
}
