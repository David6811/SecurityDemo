package org.helloai.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.helloai.gateway.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {

  private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour
  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

  @Value("${app.jwt.secret}")
  private String SECRET_KEY;

  public String generateAccessToken(Users user) {
    return Jwts.builder()
        .setSubject(String.format("%s,%s", user.getUserid(), user.getEmail()))
        .setIssuer("HelloAI")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
        .compact();
  }

  public boolean validateAccessToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException ex) {
      LOGGER.error("JWT expired", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      LOGGER.error("Token is null, empty or only whitespace", ex.getMessage());
    } catch (MalformedJwtException ex) {
      LOGGER.error("JWT is invalid", ex);
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("JWT is not supported", ex);
    } catch (SignatureException ex) {
      LOGGER.error("Signature validation failed");
    }
    return false;
  }

  private Claims parseClaims(String token) {
    return Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
  }
}
