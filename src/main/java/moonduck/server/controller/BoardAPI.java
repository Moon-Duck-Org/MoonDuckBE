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
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import moonduck.server.dto.response.BoardResponse;
import moonduck.server.exception.ErrorResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "문덕 게시판 API", description = "문덕 전체 카테고리 관련 API")
@ApiResponse(responseCode = "200", description = "OK")

@RequestMapping("/api/review")
public interface BoardAPI {

    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
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
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "BO004", description = "서버에 문의해주세요.",
                                    value = """
                                            {"code": "BO004", "message": "파일 처리 중 오류가 발생했습니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BoardResponse> savePost(
            @Parameter(description = "이미지 배열(MultipartFile[], 개수 검증은 처리되어 있지 않습니다.)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @Parameter(description = "board 데이터(application/json 형식으로 받습니다.)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("boardDto") BoardRequest boardDto,
            @Parameter(hidden = true) @LoginUserId Long userId
    );


    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "BO001", description = "존재하지 않는 리뷰의 id가 요청되었습니다.",
                                    value = """
                                            {"code": "BO001", "message": "존재하지 않는 리뷰입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "BO004", description = "서버에 문의해주세요.",
                                    value = """
                                            {"code": "BO004", "message": "파일 처리 중 오류가 발생했습니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BoardResponse> updatePost(
            @Parameter(description = "이미지 배열(MultipartFile[], 개수 검증은 처리되어 있지 않습니다.)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @Parameter(description = "board 수정 데이터(application/json 형식으로 받습니다.)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("boardDto") BoardEditRequest boardDto,
            @Parameter(hidden = true) @LoginUserId Long userId
    );


    @Operation(summary = "리뷰 전체 리스트", description = "리뷰 전체 리스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "BO003", description = "필턴는 LATEST/OLDEST/RATE 중 하나입니다.",
                                    value = """
                                            {"code": "BO003", "message": "잘못된 필터 조건입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @GetMapping("/all")
    ResponseEntity<Page<BoardResponse>> findPosts(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    );


    @Operation(summary = "카테고리별 리스트", description = "리뷰 카테고리별 리스트를 가져옵니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "BO002", description = "카테고리는 MOVIE/BOOK/DRAMA/CONCERT 중 하나입니다.",
                                    value = """
                                            {"code": "BO002", "message": "잘못된 카테고리입니다."}
                                            """),
                            @ExampleObject(name = "BO003", description = "필턴는 LATEST/OLDEST/RATE 중 하나입니다.",
                                    value = """
                                            {"code": "BO003", "message": "잘못된 필터 조건입니다."}
                                            """)
                    }, schema = @Schema(implementation = ErrorResponse.class)
            ))
    })
    @GetMapping("")
    ResponseEntity<Page<BoardResponse>> search(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestParam(name = "category") String category,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    );

    @Operation(summary = "리뷰 상세페이지", description = "리뷰 하나의 상세 정보를 가져옵니다.")
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
    @GetMapping("/detail")
    ResponseEntity<BoardResponse> findPost(@RequestParam(name = "boardId") Long boardId);

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(name = "true", description = "리뷰 삭제 완료",
                                    value = "true"
                            ),
                            @ExampleObject(name = "false", description = "리뷰 삭제 실패",
                                    value = "false"
                            )
                    })),
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
    @DeleteMapping("")
    ResponseEntity<Boolean> delete(@RequestParam(name = "boardId") Long boardId);
}
