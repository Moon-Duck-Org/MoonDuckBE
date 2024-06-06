package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import moonduck.server.config.annotation.LoginUserId;
import moonduck.server.dto.request.UserEditRequest;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "유저 API", description = "유저 관련 API")
@ApiResponse(responseCode = "200", description = "OK")

@RequestMapping("/user")
public interface UserAPI {

    @Operation(summary = "회원 조회", description = "디바이스 id에 해당하는 유저 정보를 조회합니다.")
    @GetMapping("")
    ResponseEntity<UserInfoResponse> getUserInfo(@Parameter(hidden = true) @LoginUserId Long userId);

    @Operation(summary = "닉네임 수정", description = "디바이스 id에 해당하는 유저의 닉네임을 수정합니다.")
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(
            examples = {
                    @ExampleObject(name = "중복된 닉네임",
                            description = "닉네임이 중복되어 다른 닉네임으로 설정해야 합니다.",
                            value = "중복된 닉네임입니다."
                    )
            }))
    @PutMapping("/nickname")
    ResponseEntity<UserResponse> editNickname(@Parameter(hidden = true) @LoginUserId Long userId , @RequestBody UserEditRequest userEditInfo);
}
