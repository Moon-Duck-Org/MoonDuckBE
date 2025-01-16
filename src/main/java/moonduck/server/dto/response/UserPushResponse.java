package moonduck.server.dto.response;

import lombok.Builder;
import moonduck.server.entity.User;

@Builder
public record UserPushResponse(
        Long userId,
        String push
) {

    public static UserPushResponse from(User user) {
        return UserPushResponse.builder()
                .userId(user.getId())
                .push(user.getPush())
                .build();
    }

}
