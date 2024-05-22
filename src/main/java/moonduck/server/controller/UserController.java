package moonduck.server.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.UserEditDTO;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        return ResponseEntity.ok(true);
    }

    @PutMapping("/nickname")
    public ResponseEntity<User> editNickname(@RequestBody UserEditDTO userEditInfo) {


        return ResponseEntity.ok(new User());
    }
}
