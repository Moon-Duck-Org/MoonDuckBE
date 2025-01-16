package moonduck.server.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientSecretDTO {

    @Schema(description = "Apple Developer Key Id", example = "ABC123DEFG")
    private String keyId;

    @Schema(description = "Apple Developer Team Id", example = "DEF123GHIJ")
    private String teamId;

    @Schema(description = "audience", example = "https://appleid.apple.com")
    private String audience = "https://appleid.apple.com";

    @Schema(description = "Bundle Id", example = "com.mytest.app")
    private String subject;

}
