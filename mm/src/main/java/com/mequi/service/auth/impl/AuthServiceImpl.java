package com.mequi.service.auth.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mequi.service.auth.AuthService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @__(@Inject))
public class AuthServiceImpl implements AuthService {

  private static final String SECRET = System.getenv("jwt_secret");
  private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);
  private static final long EXPIRATION_TIME_20_MINUTES = 1200000;


  @Override
  public String generateToken(Long userId) {
    final var expire = Instant.now().plusMillis(EXPIRATION_TIME_20_MINUTES);
    return JWT.create()
        .withSubject(userId.toString())
        .withExpiresAt(expire)
        .sign(ALGORITHM);
  }

  @Override
  public String validateToken(String token) {
    final var verifier = JWT.require(ALGORITHM).build();
    final var jwt = verifier.verify(token);
    return jwt.getSubject();
  }
}
