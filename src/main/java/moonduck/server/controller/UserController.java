package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.UserEditDTO;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저 관련 API")
@ApiResponse(responseCode = "200", description = "OK")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "로그인", description = "로그인을 수행합니다. 만약 회원이 아니라면 회원가입을 자동으로 수행합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "true",
                            description = "추가 작업 없이 정상적으로 로그인이 된 경우입니다.",
                            value = """
                                    true
                                    """
                    ),
                    @ExampleObject(name = "false",
                            description = "닉네임 설정이 되어 있지 않아 닉네임 설정이 필요합니다.",
                            value = """
                                    false
                                    """
                    )
            }))
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserLoginDTO userInfo) {
        User user = userService.tryRegistrationAndReturnUser(userInfo);

        if (user.getNickname() == null) {
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    @Operation(summary = "닉네임 수정", description = "디바이스 id에 해당하는 유저의 닉네임을 수정합니다.")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "unauthorized",
                            description = "디바이스 id에 해당하는 유저가 없는 경우 발생합니다.",
                            value = """
                                    존재하지 않는 유저입니다.
                                    """
                    )
            }))
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "bad_request",
                            description = "이미 존재하는 중복된 닉네임입니다.",
                            value = """
                                    중복된 닉네임입니다.
                                    """
                    )
            }))
    @PutMapping("/nickname")
    public ResponseEntity<User> editNickname(@RequestBody UserEditDTO userEditInfo) {
        // 해당 이메일이 존재하지 않다면 4xx 반환
        // 존재한다면 해당 유저의 닉네임 수정
        User editedUser = userService.editNickname(userEditInfo);

        return ResponseEntity.ok(editedUser);
    }

    @Operation(summary = "회원 조회", description = "디바이스 id에 해당하는 유저 정보를 조회합니다.")
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "unauthorized",
                            description = "디바이스 id에 해당하는 유저가 없는 경우 발생합니다.",
                            value = """
                                    존재하지 않는 유저입니다.
                                    """
                    )
            }))
    @GetMapping("")
    public ResponseEntity<User> getUserInfo(
            @Parameter(description = "디바이스 id", example = "/user?deviceId=123451354354534355")
            @RequestParam(name = "deviceId")
            String deviceId
    ) {
        User user = userService.getUser(deviceId);
        return ResponseEntity.ok(user);
    }
}
