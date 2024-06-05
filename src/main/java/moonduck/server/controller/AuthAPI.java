package moonduck.server.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "로그인, 토큰 재발급 API")
@ApiResponse(responseCode = "200", description = "OK")

@RequestMapping("/auth")
public interface AuthAPI {


}
