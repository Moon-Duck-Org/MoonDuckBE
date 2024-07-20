package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import moonduck.server.config.annotation.LoginUserId;
import moonduck.server.dto.auth.ClientSecretDTO;
import moonduck.server.dto.auth.RevokeTokenDTO;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.dto.request.ReissueRequest;
import moonduck.server.dto.response.LoginResponse;
import moonduck.server.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "로그인, 토큰 재발급 API")
@ApiResponse(responseCode = "200", description = "OK")

@RequestMapping("/auth")
public interface AuthAPI {

    @Operation(summary = "로그인", description = "소셜 로그인으로부터 받은 회원 정보로 로그인을 수행합니다. 회원 가입을 자동으로 수행하고 닉네임 설정 여부를 반환합니다.")
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userInfo);


    @Operation(summary = "access 토큰 재발급", description = "기존 refresh 토큰으로 새로운 access 토큰과 refresh 토큰을 발급받습니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "AU001", description = "토큰 필드값을 확인해주세요.",
                                    value = """
                                            {"code": "AU001", "message": "토큰이 존재하지 않습니다."}
                                            """),
                            @ExampleObject(name = "AU002", description = "토큰 필드값을 확인해주세요.",
                                    value = """
                                            {"code": "AU002", "message": "잘못된 유형의 토큰입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "AU005", description = "토큰이 유효하지 않습니다.",
                                    value = """
                                            {"code": "AU005", "message": "유효하지 않은 토큰입니다."}
                                            """),
                            @ExampleObject(name = "AU006", description = "토큰으로부터 유저 인증정보를 얻지 못했습니다.",
                                    value = """
                                            {"code": "AU006", "message": "인증정보가 등록되지 않았습니다. 서버에 문의해주세요."}
                                            """),
                            @ExampleObject(name = "AU008", description = "유효하지 않은 rt 사용으로 인해 rt가 삭제되었습니다.",
                                    value = """
                                            {"code": "AU008", "message": "유효하지 않은 refresh token입니다. 재로그인 해주세요."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "AU003", description = "토큰이 만료되었습니다.",
                                    value = """
                                            {"code": "AU003", "message": "만료된 토큰입니다."}
                                            """),
                            @ExampleObject(name = "AU004", description = "토큰이 만료되지 않은 상태로 reissue를 요청했습니다.",
                                    value = """
                                            {"code": "AU004", "message": "아직 토큰이 만료되지 않았습니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @PostMapping("/reissue")
    ResponseEntity<TokenDTO> reissue(@RequestBody ReissueRequest request);

    @Operation(summary = "revoke 토큰 생성", description = "client secret(revoke Token)을 발급받습니다. 동시에 refreshToken이 무효 처리됩니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "AU007", description = "토큰 생성에 실패했습니다.",
                                    value = """
                                            {"code": "AU007", "message": "revoke Token 생성에 실패했습니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @PostMapping("/revoke")
    ResponseEntity<RevokeTokenDTO> getRevoke(
            @RequestBody ClientSecretDTO request,
            @Parameter(hidden = true) @LoginUserId Long userId
    );
}
