package moonduck.server.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.entity.Refresh;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.jwt.JWTUtil;
import moonduck.server.repository.RefreshRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Transactional
    public TokenDTO generateAndSaveNewToken(Long userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        Refresh refresh = new Refresh(userId, refreshToken, refreshExpiration);
        refreshRepository.deleteAllByUserId(userId);
        refreshRepository.save(refresh);

        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    public TokenDTO reissue(String accessToken, String refreshToken) {
        if (accessToken == null || refreshToken == null) {
            throw new ErrorException(ErrorCode.NO_TOKEN);
        } else if (!jwtUtil.isValidToken(accessToken) || !jwtUtil.isValidToken(refreshToken)) {
            throw new ErrorException(ErrorCode.INVALID_TOKEN);
        }

        if (!jwtUtil.isExpired(accessToken)) {
            throw new ErrorException(ErrorCode.TOKEN_NOT_EXPIRED);
        }

        if (jwtUtil.isExpired(refreshToken)) {
            throw new ErrorException(ErrorCode.TOKEN_EXPIRED);
        } else if (!jwtUtil.getCategory(refreshToken).equals("refresh")) {
            throw new ErrorException(ErrorCode.NOT_MATCH_CATEGORY);
        }

        Refresh refresh = refreshRepository.findByRefresh(refreshToken)
                .orElseThrow(() -> new ErrorException(ErrorCode.INVALID_TOKEN));

        String newAccessToken = jwtUtil.createAccessToken(refresh.getUserId());
        String newRefreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        refresh.changeToken(newRefreshToken, refreshExpiration);

        return new TokenDTO(newAccessToken, newRefreshToken);
    }
}
