package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.User;

@Builder
public record UserResponse(
        Long userId,
        String nickname,
        String push
) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .push(user.getPush())
                .build();
    }

}
