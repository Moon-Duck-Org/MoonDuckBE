package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.dto.auth.TokenDTO;

@Builder
public record LoginResponse(
        Long userId,
        String accessToken,
        String refreshToken,
        Boolean isHaveNickname,
        String ispush
) {
    public static LoginResponse of(Long userId, TokenDTO tokens, Boolean isHaveNickname, String ispush) {
        return LoginResponse.builder()
                .userId(userId)
                .accessToken(tokens.getAccessToken())
                .refreshToken(tokens.getRefreshToken())
                .isHaveNickname(isHaveNickname)
                .ispush(ispush)
                .build();
    }
}