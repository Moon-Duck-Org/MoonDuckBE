package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.request.UserEditRequest;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저 관련 API")
@ApiResponse(responseCode = "200", description = "OK")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "닉네임 수정", description = "디바이스 id에 해당하는 유저의 닉네임을 수정합니다.")
    @PutMapping("/nickname")
    public ResponseEntity<User> editNickname(@RequestBody UserEditRequest userEditInfo) {
        // 해당 이메일이 존재하지 않다면 4xx 반환
        // 존재한다면 해당 유저의 닉네임 수정
        User editedUser = userService.editNickname(userEditInfo);

        return ResponseEntity.ok(editedUser);
    }

    @Operation(summary = "회원 조회", description = "디바이스 id에 해당하는 유저 정보를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<UserInfoResponse> getUserInfo(
//            @RequestParam(name = "deviceId")
//            String deviceId
    ) {
        String deviceId = SecurityContextHolder.getContext().getAuthentication().getName();

        UserInfoResponse userInfo = userService.getUser(deviceId);
        return ResponseEntity.ok(userInfo);
    }

}
