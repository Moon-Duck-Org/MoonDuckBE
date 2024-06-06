package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.AuthAPI;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.dto.request.ReissueRequest;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.dto.response.LoginResponse;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import moonduck.server.service.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthAPI {

    private final UserService userService;
    private final AuthService authService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest userInfo) {
        User user = userService.tryRegistrationAndReturnUser(userInfo);

        TokenDTO tokens = authService.generateAndSaveNewToken(user.getDeviceId());
        Boolean isHaveNickname = user.getNickname() != null;

        LoginResponse loginResponse = LoginResponse.of(tokens, isHaveNickname);
        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public ResponseEntity<TokenDTO> reissue(ReissueRequest request) {
        TokenDTO tokens = authService.reissue(request.getAccess(), request.getRefresh());

        return ResponseEntity.ok(tokens);
    }
}
