package moonduck.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moonduck.server.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private Long id;
    private String deviceId;
    private String nickname;

    private long
            MOVIE,
            BOOK,
            DRAMA,
            CONCERT;

    public UserInfoDTO(User user) {
        this.id = user.getId();
        this.deviceId = user.getDeviceId();
        this.nickname = user.getNickname();
    }
}
