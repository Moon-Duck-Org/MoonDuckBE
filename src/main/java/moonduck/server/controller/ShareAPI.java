package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import moonduck.server.config.annotation.LoginUserId;
import moonduck.server.dto.response.ShareUrlResponse;
import moonduck.server.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "공유 API", description = "공유 관련 API")
@ApiResponse(responseCode = "200", description = "OK")

@RequestMapping("/share")
public interface ShareAPI {

    @Operation(summary = "공유 URL 조회", description = "공유할 리뷰의 URL 파라미터를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "BO001", description = "존재하지 않는 리뷰의 id가 요청되었습니다.",
                                    value = """
                                            {"code": "BO001", "message": "존재하지 않는 리뷰입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @GetMapping("/getShareUrl/{boardId}")
    @ResponseBody
    ResponseEntity<ShareUrlResponse> getShareUrl(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @Parameter(description = "공유할 리뷰의 boardId", example = "1")
            @PathVariable("boardId") Long boardId);

    @Operation(summary = "공유 페이지 접속", description = "공유된 페이지의 리뷰를 조회합니다.")
    @GetMapping("/{param}")
    String getSharePage(
            @Parameter(description = "조회된 공유 페이지의 url 파라미터", example = "faeafaefa-afefafea-afefa")
            @PathVariable("param") String param,
            Model model
    );
}
