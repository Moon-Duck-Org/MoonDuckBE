package moonduck.server.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.controller.AuthAPI;
import moonduck.server.dto.auth.ClientSecretDTO;
import moonduck.server.dto.auth.RevokeTokenDTO;
import moonduck.server.dto.auth.TokenDTO;
import moonduck.server.dto.request.LoginRequest;
import moonduck.server.dto.request.ReissueRequest;
import moonduck.server.dto.response.LoginResponse;
import moonduck.server.dto.response.UserIdResponse;
import moonduck.server.entity.User;
import moonduck.server.service.UserService;
import moonduck.server.service.security.AuthService;
import org.springframework.http.ResponseEntity;
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

        TokenDTO tokens = authService.generateAndSaveNewToken(user.getId());
        Boolean isHaveNickname = user.getNickname() != null;
        String ispush = String.valueOf('Y');

        LoginResponse loginResponse = LoginResponse.of(user.getId(), tokens, isHaveNickname, ispush);
        return ResponseEntity.ok(loginResponse);
    }

    @Override
    public ResponseEntity<TokenDTO> reissue(ReissueRequest request) {
        TokenDTO tokens = authService.reissue(request.getAccessToken(), request.getRefreshToken(), request.getUserId(), request.getPush());

        return ResponseEntity.ok(tokens);
    }

    @Override
    public ResponseEntity<RevokeTokenDTO> getRevoke(ClientSecretDTO request, Long userId) {
        RevokeTokenDTO revoke = authService.getRevoke(request, userId);

        return ResponseEntity.ok(revoke);
    }

    @Override
    public ResponseEntity<UserIdResponse> logout(Long userId) {
        return ResponseEntity.ok(authService.logout(userId));
    }

    @Override
    public ResponseEntity<UserIdResponse> userExit(Long userId) {
        authService.logout(userId);
        UserIdResponse deletedId = userService.deleteUser(userId);

        return ResponseEntity.ok(deletedId);
    }
}
