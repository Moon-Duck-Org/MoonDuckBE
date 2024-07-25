package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.dto.auth.TokenDTO;

@Builder
public record LoginResponse(
        Long userId,
        String accessToken,
        String refreshToken,
        Boolean isHaveNickname
) {
    public static LoginResponse of(Long userId, TokenDTO tokens, Boolean isHaveNickname) {
        return LoginResponse.builder()
                .userId(userId)
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .isHaveNickname(isHaveNickname)
                .build();
    }
}