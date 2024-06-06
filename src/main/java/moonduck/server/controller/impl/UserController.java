package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.UserAPI;
import moonduck.server.dto.request.UserEditRequest;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserAPI {

    private final UserService userService;

    @Override
    public ResponseEntity<User> editNickname(@RequestBody UserEditRequest userEditInfo) {
        // 해당 이메일이 존재하지 않다면 4xx 반환
        // 존재한다면 해당 유저의 닉네임 수정
        User editedUser = userService.editNickname(userEditInfo);

        return ResponseEntity.ok(editedUser);
    }

    @Override
    public ResponseEntity<UserInfoResponse> getUserInfo(
//            @RequestParam(name = "deviceId")
//            String deviceId
    ) {
        String deviceId = SecurityContextHolder.getContext().getAuthentication().getName();

        UserInfoResponse userInfo = userService.getUser(deviceId);
        return ResponseEntity.ok(userInfo);
    }

}
