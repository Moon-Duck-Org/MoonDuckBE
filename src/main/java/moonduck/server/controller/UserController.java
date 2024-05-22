package moonduck.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.UserEditDTO;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserLoginDTO userInfo) {
        log.info("userInfo.getEmail() = {}", userInfo.getEmail());
        log.info("userInfo.getName() = {}", userInfo.getName());

        // db에 동일한 이메일이 존재한다면 true(닉네임 설정 안해도됨)
        // db에 동일한 이메일이 없다면 false(닉네임 설정 해야됨, 자동으로 회원가입 진행됨)
        if (userService.tryRegistration(userInfo)) {
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    @PutMapping("/nickname")
    public ResponseEntity<User> editNickname(@RequestBody UserEditDTO userEditInfo) {
        // 해당 이메일이 존재하지 않다면 4xx 반환
        // 존재한다면 해당 유저의 닉네임 수정
        User editedUser = userService.editNickname(userEditInfo);

        return ResponseEntity.ok(editedUser);
    }
}
