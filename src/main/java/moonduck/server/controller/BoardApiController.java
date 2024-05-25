package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.service.BoardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "문덕 게시판 API", description = "문덕 전체 카테고리 관련 API")
@ApiResponse(responseCode = "200", description = "OK")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    private final BoardServiceImpl boardService;

    //Create 생성
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Board.class)
    ))
    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "unauthorized",
                            description = "디바이스 id에 해당하는 유저가 없는 경우 발생합니다.",
                            value = """
                                    존재하지 않는 유저입니다.
                                    """
                    )
            }))
    @PostMapping("/api/review")
    public ResponseEntity<Board> savePost(@RequestBody BoardRequestDTO request) {
        Board board = boardService.savePost(request);

        return ResponseEntity.ok(board);
    }


    //Update 수정
    @Operation(summary = "리뷰 수정", description = "리뷰를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Board.class)
    ))
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "잘못된 board id",
                            description = "존재하지 않는 board id입니다.",
                            value = """
                                    존재하지 않는 리뷰입니다.
                                    """
                    ),
                    @ExampleObject(name = "잘못된 필터 조건",
                            description = "잘못된 필터 조건입니다. 필터 조건은 다음과 같아야 합니다. - LATEST, OLDEST, RATE",
                            value = """
                                    잘못된 필터 조건입니다.
                                    """
                    )
            }))
    @PutMapping("/api/review")
    public ResponseEntity<Board> updatePost(@RequestBody BoardEditDTO boardDto) {

        Board editedBoard = boardService.update(boardDto);

        return ResponseEntity.ok(editedBoard);
    }

    //Read
    @Operation(summary = "리뷰 전체 리스트", description = "리뷰 전체 리스트를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Board.class))
    ))
    @GetMapping("/api/review/all")
    public ResponseEntity<List<Board>> findPosts(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "filter", required = false) String filter
    ){

        List<Board> reviews = boardService.getAllReview(userId, filter);

        return ResponseEntity.ok(reviews);
    }

    // 카테고리별 리스트 조회
    @Operation(summary = "카테고리별 리스트", description = "리뷰 카테고리별 리스트를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Board.class))
    ))
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "잘못된 카테고리",
                            description = "카테고리 필드가 잘못되었습니다. 카테고리는 다음 중 하나입니다. - MOVIE, BOOK, DRAMA, CONCERT",
                            value = """
                                    잘못된 카테고리입니다.
                                    """
                    ),
                    @ExampleObject(name = "잘못된 필터 조건",
                            description = "잘못된 필터 조건입니다. 필터 조건은 다음과 같아야 합니다. - LATEST, OLDEST, RATE",
                            value = """
                                    잘못된 필터 조건입니다.
                                    """
                    )
            }))
    @GetMapping("/api/review")
    public ResponseEntity<List<Board>> search(
            @RequestParam(name = "userId") Long userId,
            @RequestParam(name = "category") String category,
            @RequestParam(name = "filter", required = false) String filter
    ) {
        List<Board> reviewWithCategory = boardService.getReviewWithCategory(userId, category, filter);

        return ResponseEntity.ok(reviewWithCategory);
    }



    //Read
    @Operation(summary = "리뷰 상세페이지", description = "리뷰 하나의 상세 정보를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Board.class)
    ))
    @ApiResponse(responseCode = "400", description = "BAD_REQUEST", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "bad_request",
                            description = "존재하지 않는 board id입니다.",
                            value = """
                                    존재하지 않는 리뷰입니다.
                                    """
                    )
            }))
    @GetMapping("/api/review/detail")
    public ResponseEntity<Board> findPost(@RequestParam(name = "boardId") Long boardId){
        Board review = boardService.getReview(boardId);

        return ResponseEntity.ok(review);
    }


    //Delete
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "true",
                            description = "리뷰 삭제 완료",
                            value = """
                                    true
                                    """
                    ),
                    @ExampleObject(name = "false",
                            description = "리뷰 삭제 실패",
                            value = """
                                    false
                                    """
                    )
            }))
    @DeleteMapping("/api/review")
    public ResponseEntity<Boolean> delete(@RequestParam(name = "boardId") Long boardId){
        boardService.deletePost(boardId);

        return ResponseEntity.ok(true);
    }

}