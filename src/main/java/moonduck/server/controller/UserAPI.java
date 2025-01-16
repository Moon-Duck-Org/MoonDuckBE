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
import moonduck.server.dto.request.UserEditRequest;
import moonduck.server.dto.request.UserPushRequest;
import moonduck.server.dto.response.UserInfoResponse;
import moonduck.server.dto.response.UserPushResponse;
import moonduck.server.dto.response.UserResponse;
import moonduck.server.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 API", description = "유저 관련 API")
@ApiResponse(responseCode = "200", description = "OK")
@RequestMapping("/user")
public interface UserAPI {
    @Operation(summary = "회원 조회", description = "유저 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "US001", description = "존재하지 않는 유저의 id가 요청되었습니다.",
                                    value = """
                                            {"code": "US001", "message": "존재하지 않는 유저입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
    })

    @GetMapping("")
    ResponseEntity<UserInfoResponse> getUserInfo(@Parameter(hidden = true) @LoginUserId Long userId);

    @Operation(summary = "닉네임 수정", description = "유저의 닉네임을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "US002", description = "닉네임이 중복되었습니다.",
                                    value = """
                                            {"code": "US002", "message": "중복된 닉네임입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "US001", description = "존재하지 않는 유저의 id가 요청되었습니다.",
                                    value = """
                                            {"code": "US001", "message": "존재하지 않는 유저입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })

    @PutMapping("/nickname")
    ResponseEntity<UserResponse> editNickname(@Parameter(hidden = true) @LoginUserId Long userId , @RequestBody UserEditRequest userEditInfo);

    @Operation(summary = "사용자 푸시 설정 수정", description = "유저의 푸시 기능 설정을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "US001", description = "존재하지 않는 유저의 id가 요청되었습니다.",
                                    value = """
                                            {"code": "US001", "message": "존재하지 않는 유저입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })

    @PutMapping("/push")
    ResponseEntity<UserPushResponse> editPush(@Parameter(hidden = true) @LoginUserId Long userId , @RequestBody UserPushRequest userEditInfo);

}
