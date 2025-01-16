package moonduck.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("share")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Share {

    @Id
    private String uuid;

    private Long boardId;

    @TimeToLive
    private Long expiration;

}
