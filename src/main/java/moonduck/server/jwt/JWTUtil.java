package moonduck.server.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import moonduck.server.dto.auth.ClientSecretDTO;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    @Value("${spring.jwt.accessTokenExpiration}")
    private Long accessExpiredMs;
    @Value("${spring.jwt.refreshTokenExpiration}")
    private Long refreshExpiredMs;
    @Value("${spring.jwt.revokeTokenExpiration}")
    private Long revokeExpiredMs;

    private static final String revokeKeyPath = "tmp";

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

    public String createClientSecret(ClientSecretDTO dto) {
        long now = System.currentTimeMillis();

        HashMap<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("alg", "ES256");
        jwtHeader.put("kid", dto.getKeyId());

        return Jwts.builder()
                .setHeader(jwtHeader)
                .claim("iss", dto.getTeamId())
                .claim("aud", dto.getAudience())
                .claim("sub", dto.getSubject())
                .issuedAt(new Date(now))
                .expiration(new Date(now + revokeExpiredMs))
                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            InputStream keyStream = getClass().getClassLoader().getResourceAsStream(revokeKeyPath);
            byte[] keyBytes = keyStream.readAllBytes();
            keyStream.close();

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            return keyFactory.generatePrivate(spec);
        } catch (NoSuchAlgorithmException e) {
            throw new ErrorException(ErrorCode.FAIL_CREATE_REVOKE_TOKEN);
        } catch (InvalidKeySpecException e) {
            throw new ErrorException(ErrorCode.FAIL_CREATE_REVOKE_TOKEN);
        } catch (IOException e) {
            throw new ErrorException(ErrorCode.FAIL_CREATE_REVOKE_TOKEN);
        }

        // 파일에 불필요한 라인 있는 경우 replace로 제거 필요
//        InputStream privateKey = new ClassPathResource("appleAuthKey.p8").getInputStream();
//
//        String result = new BufferedReader(new InputStreamReader(privateKey)) .lines().collect(Collectors.joining("\n"));
//
//        String key = result.replace("-----BEGIN PRIVATE KEY-----\n", "")
//                .replace("-----END PRIVATE KEY-----", "");
//
//        byte[] encoded = Base64.decodeBase64(key);
//
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
//        KeyFactory keyFactory = KeyFactory.getInstance("EC");
//        return keyFactory.generatePrivate(keySpec);
    }
}
