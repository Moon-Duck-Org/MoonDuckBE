package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.UserLoginDTO;
import moonduck.server.dto.auth.ReissueDTO;
import moonduck.server.dto.auth.TokenResponseDTO;
import moonduck.server.entity.User;
import moonduck.server.jwt.JWTUtil;
import moonduck.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserLoginDTO userInfo) {
        User user = userService.tryRegistrationAndReturnUser(userInfo);

        if (user.getNickname() == null) {
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(@RequestBody ReissueDTO request) {

        return ResponseEntity.ok(new TokenResponseDTO());
    }
}
