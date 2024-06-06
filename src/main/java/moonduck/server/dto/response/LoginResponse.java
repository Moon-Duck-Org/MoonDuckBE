package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.dto.auth.TokenDTO;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken,
        Boolean isHaveNickname
) {
    public static LoginResponse of(TokenDTO tokens, Boolean isHaveNickname) {
        return LoginResponse.builder()
                .accessToken(tokens.getAccess())
                .refreshToken(tokens.getRefresh())
                .isHaveNickname(isHaveNickname)
                .build();
    }
}