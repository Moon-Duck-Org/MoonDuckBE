package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.User;

@Builder
public record UserResponse(
        Long userId,
        String nickname
) {
    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }
}
