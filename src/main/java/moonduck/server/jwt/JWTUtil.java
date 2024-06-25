package moonduck.server.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import moonduck.server.dto.auth.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    @Value("${spring.jwt.accessTokenExpiration}")
    private Long accessExpiredMs;
    @Value("${spring.jwt.refreshTokenExpiration}")
    private Long refreshExpiredMs;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Long getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", Long.class);
    }

    public Date getExpiration(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
    }

    public Boolean isExpired(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
        } catch (ExpiredJwtException e) {
            return true;
        }
        return false;
    }

    public Boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (SignatureException e) {
            return false;
        }
        return true;
    }

    public String createAccessToken(Long userId) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claim("category", "access")
                .claim("userId", userId)
                .issuedAt(new Date(now))
                .expiration(new Date(now + accessExpiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken() {
        long now = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();

        return Jwts.builder()
                .claim("category", "refresh")
                .claim("uuid", uuid)
                .issuedAt(new Date(now))
                .expiration(new Date(now + refreshExpiredMs))
                .signWith(secretKey)
                .compact();
    }
}
