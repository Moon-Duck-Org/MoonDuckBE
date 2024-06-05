package moonduck.server.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.entity.Refresh;
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

    public TokenDTO generateAndSaveNewToken(String deviceId) {
        String accessToken = jwtUtil.createAccessToken(deviceId);
        String refreshToken = jwtUtil.createRefreshToken();

        Date refreshExpiration = jwtUtil.getExpiration(refreshToken);

        Refresh refresh = new Refresh(deviceId, refreshToken, refreshExpiration);
        refreshRepository.save(refresh);

        return new TokenDTO(accessToken, refreshToken);
    }
}
