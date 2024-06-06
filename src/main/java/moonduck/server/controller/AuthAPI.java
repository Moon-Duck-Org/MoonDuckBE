package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.dto.request.ReissueRequest;
import moonduck.server.dto.response.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "로그인, 토큰 재발급 API")
@ApiResponse(responseCode = "200", description = "OK")

@RequestMapping("/auth")
public interface AuthAPI {

    @Operation(summary = "로그인", description = "소셜 로그인으로부터 받은 회원 정보로 로그인을 수행합니다. 회원 가입을 자동으로 수행하고 닉네임 설정 여부를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "true", description = "닉네임 설정이 되어 있고, 정상적으로 로그인이 된 경우입니다.",
                            value = "true"
                    ),
                    @ExampleObject(name = "false", description = "로그인은 되었지만, 닉네임 설정이 되어 있지 않아 닉네임 설정이 필요합니다.",
                            value = "false"
                    )
            }))
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userInfo);


    @Operation(summary = "access 토큰 재발급", description = "기존 refresh 토큰으로 새로운 access 토큰과 refresh 토큰을 발급받습니다.")
    @PostMapping("/reissue")
    ResponseEntity<TokenDTO> reissue(@RequestBody ReissueRequest request);
}
