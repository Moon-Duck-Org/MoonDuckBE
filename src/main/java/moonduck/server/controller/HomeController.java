package moonduck.server.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import moonduck.server.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "홈 API", description = "서버 작동 확인을 위한 테스트 API입니다, JWT 인증에 대한 오류 응답 메세지 예시도 포함하였습니다.")
@RestController
public class HomeController {

    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "INVALID", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "AU005", description = "토큰이 유효하지 않습니다.",
                                    value = """
                                            {"code": "AU005", "message": "유효하지 않은 토큰입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "EXPIRED", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "AU003", description = "토큰이 만료되었습니다.",
                                    value = """
                                            {"code": "AU003", "message": "만료된 토큰입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok("hello world");
    }
}
