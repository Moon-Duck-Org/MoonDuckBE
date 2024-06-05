package moonduck.server.service.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.entity.Refresh;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.auth.TokenException;
import moonduck.server.jwt.JWTUtil;
import moonduck.server.repository.RefreshRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Transactional
    public TokenDTO generateAndSaveNewToken(String deviceId) {
        String accessToken = jwtUtil.createAccessToken(deviceId);
        String refreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        Refresh refresh = new Refresh(deviceId, refreshToken, refreshExpiration);
        refreshRepository.deleteAllByUserId(deviceId);
        refreshRepository.save(refresh);

        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    public TokenDTO reissue(String accessToken, String refreshToken) {
        if (accessToken == null || refreshToken == null) {
            throw new TokenException(ErrorCode.NO_TOKEN);
        }

        if (!jwtUtil.getCategory(accessToken).equals("access")) {
            throw new TokenException(ErrorCode.NOT_MATCH_CATEGORY);
        }

        String deviceId = jwtUtil.getDeviceId(accessToken);

        if (!jwtUtil.getCategory(refreshToken).equals("refresh")) {
            throw new TokenException(ErrorCode.NOT_MATCH_CATEGORY);
        } else if (jwtUtil.isExpired(refreshToken)) {
            throw new TokenException(ErrorCode.TOKEN_EXPIRED);
        }

        Refresh refresh = refreshRepository.findByRefresh(refreshToken)
                .orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));

        String newAccessToken = jwtUtil.createAccessToken(deviceId);
        String newRefreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        refresh.changeToken(newRefreshToken, refreshExpiration);

        return new TokenDTO(newAccessToken, newRefreshToken);
    }
}
