package moonduck.server.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RevokeTokenDTO {

    private String revokeToken;

}
