package moonduck.server.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "기타 API", description = "개인 정보 처리방침, 서비스 이용약관, 공유페이지")
@ApiResponse(responseCode = "200", description = "OK")
@Controller
public class ShareController {

    @GetMapping("contract")
    public String contract(){
        return "contract";
    }

    @GetMapping("privacy")
    public String privacy(){
        return "privacy";
    }

}
