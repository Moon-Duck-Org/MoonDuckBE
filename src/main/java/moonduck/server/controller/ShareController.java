package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "기타 API", description = "개인 정보 처리방침, 서비스 이용약관, 공유페이지")
@ApiResponse(responseCode = "200", description = "OK")
@Controller
public class ShareController {

    @Operation(summary = "사용자 이용약관", description = "사용자 이용약관을 가져옵니다.")
    @GetMapping("contract")
    public String contract(){
        return "contract";
    }

    @Operation(summary = "개인정보 처리방침", description = "개인정보 처리방침을 가져옵니다.")
    @GetMapping("privacy")
    public String privacy(){
        return "privacy";
    }

}
