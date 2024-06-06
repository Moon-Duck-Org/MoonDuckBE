package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import moonduck.server.entity.Board;
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
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Board> savePost(
            @Parameter(description = "이미지 배열(MultipartFile[], 개수 검증은 처리되어 있지 않습니다.)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @Parameter(description = "board 데이터(application/json 형식으로 받습니다.)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("boardDto") BoardRequest boardDto
    );

    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Board> updatePost(
            @Parameter(description = "이미지 배열(MultipartFile[], 개수 검증은 처리되어 있지 않습니다.)", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart(value = "images", required = false) MultipartFile[] images,
            @Parameter(description = "board 수정 데이터(application/json 형식으로 받습니다.)", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
            @RequestPart("boardDto") BoardEditRequest boardDto
    );

    @Operation(summary = "리뷰 전체 리스트", description = "리뷰 전체 리스트를 가져옵니다.")
    @GetMapping("/all")
    ResponseEntity<Page<Board>> findPosts(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    );

    @Operation(summary = "카테고리별 리스트", description = "리뷰 카테고리별 리스트를 가져옵니다.")
    @GetMapping("")
    ResponseEntity<Page<Board>> search(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "category") String category,
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(name = "offset") int offset,
            @RequestParam(name = "size") int size
    );

    @Operation(summary = "리뷰 상세페이지", description = "리뷰 하나의 상세 정보를 가져옵니다.")
    @GetMapping("/detail")
    ResponseEntity<Board> findPost(@RequestParam(name = "boardId") Long boardId);

    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "true", description = "리뷰 삭제 완료",
                            value = "true"
                    ),
                    @ExampleObject(name = "false", description = "리뷰 삭제 실패",
                            value = "false"
                    )
            }))
    @DeleteMapping("")
    ResponseEntity<Boolean> delete(@RequestParam(name = "boardId") Long boardId);
}
