package moonduck.server.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
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
    private final Long accessExpiredMs = 600000L;
    private final Long refreshExpiredMs = 86400000L;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getDeviceId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public Boolean isValidToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        } catch (SignatureException e) {
            return false;
        }
        return true;
    }

    public String createAccessToken(String deviceId) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claim("category", "access")
                .claim("deviceId", deviceId)
                .issuedAt(new Date(now))
                .expiration(new Date(now + accessExpiredMs))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken() {
        long now = System.currentTimeMillis();
        UUID uuid = UUID.randomUUID();

        return Jwts.builder()
                .claim("category", "access")
                .claim("uuid", uuid)
                .issuedAt(new Date(now))
                .expiration(new Date(now + refreshExpiredMs))
                .signWith(secretKey)
                .compact();
    }
}
