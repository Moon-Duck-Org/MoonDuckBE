package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.UserAPI;
import moonduck.server.dto.request.UserEditRequest;
import moonduck.server.dto.request.UserPushRequest;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.dto.response.UserPushResponse;
import moonduck.server.dto.response.UserResponse;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserAPI {

    private final UserService userService;

    @Override
    public ResponseEntity<UserInfoResponse> getUserInfo(Long userId) {
        UserInfoResponse userInfo = userService.getUser(userId);

        return ResponseEntity.ok(userInfo);
    }

    @Override
    public ResponseEntity<UserResponse> editNickname(Long userId, UserEditRequest userEditInfo) {
        UserResponse editedUser = userService.editNickname(userId, userEditInfo.getNickname());

        return ResponseEntity.ok(editedUser);
    }

    @Override
    public ResponseEntity<UserPushResponse> editPush(Long userId, UserPushRequest userEditInfo) {
        UserPushResponse editedPush = userService.editPush(userId, userEditInfo.getPush());

        return ResponseEntity.ok(editedPush);
    }

}
