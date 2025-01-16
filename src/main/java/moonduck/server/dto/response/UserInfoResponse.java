package moonduck.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonduck.server.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponse {

    private Long userId;
    private String nickname;
    private String push;

    private long
            MOVIE,
            BOOK,
            DRAMA,
            CONCERT;

    public UserInfoResponse(User user) {
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.push = user.getPush();
    }

}
