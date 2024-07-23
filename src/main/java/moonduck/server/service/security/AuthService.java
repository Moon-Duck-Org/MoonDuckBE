package moonduck.server.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.auth.ClientSecretDTO;
import moonduck.server.dto.auth.RevokeTokenDTO;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.entity.RefreshToken;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.jwt.JWTUtil;
import moonduck.server.repository.redis.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final JWTUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenDTO generateAndSaveNewToken(Long userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        Long expirationSecond = refreshExpiration.getTime() / 1000;
        RefreshToken rt = new RefreshToken(userId, refreshToken, expirationSecond);
        refreshTokenRepository.save(rt);

        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    public TokenDTO reissue(String accessToken, String refreshToken, Long userId) {
        if (accessToken == null || refreshToken == null) {
            throw new ErrorException(ErrorCode.NO_TOKEN);
        }

        if (!jwtUtil.isExpired(accessToken)) {
            throw new ErrorException(ErrorCode.TOKEN_NOT_EXPIRED);
        }

        if (jwtUtil.isExpired(refreshToken)) {
            throw new ErrorException(ErrorCode.TOKEN_EXPIRED);
        } else if (!jwtUtil.getCategory(refreshToken).equals("refresh")) {
            throw new ErrorException(ErrorCode.NOT_MATCH_CATEGORY);
        } else if (!jwtUtil.isValidToken(refreshToken)) {
            throw new ErrorException(ErrorCode.INVALID_TOKEN);
        }

        RefreshToken rt = refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!rt.getToken().equals(refreshToken)) {
            refreshTokenRepository.deleteById(userId);
            throw new ErrorException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtUtil.createAccessToken(userId);
        String newRefreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        Long expirationSecond = refreshExpiration.getTime() / 1000;
        RefreshToken newRt = new RefreshToken(userId, newRefreshToken, expirationSecond);
        refreshTokenRepository.save(newRt);

        return new TokenDTO(newAccessToken, newRefreshToken);
    }

    public RevokeTokenDTO getRevoke(ClientSecretDTO clientSecretDTO, Long userId) {
        refreshTokenRepository.deleteById(userId);

        String revokeToken = jwtUtil.createClientSecret(clientSecretDTO);

        return RevokeTokenDTO.builder()
                .revokeToken(revokeToken)
                .build();
    }
}
