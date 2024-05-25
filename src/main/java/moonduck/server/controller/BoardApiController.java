package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.repository.BoardRepository;
import moonduck.server.service.BoardServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name = "문덕 게시판 API", description = "문덕 전체 카테고리 관련 API")
@ApiResponse(responseCode = "200", description = "OK")
@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardApiController {

    private final BoardServiceImpl boardService;
    private final BoardRepository boardRepository;

    //Create 생성
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "true",
                            description = "리뷰 생성되었습니다.",
                            value = """
                                    true
                                    """
                    ),
                    @ExampleObject(name = "false",
                            description = "리뷰 생성 실패하였습니다.",
                            value = """
                                    false
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
            examples = {
                    @ExampleObject(name = "true",
                            description = "리뷰 수정 완료",
                            value = """
                                    true
                                    """
                    ),
                    @ExampleObject(name = "false",
                            description = "리뷰 수정 실패",
                            value = """
                                    false
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
            examples = {
                    @ExampleObject(name = "true",
                            description = "리뷰 전체 리스트 로딩 완료",
                            value = """
                                    true
                                    """
                    ),
                    @ExampleObject(name = "false",
                            description = "리뷰 전체 리스트 로딩 실패",
                            value = """
                                    false
                                    """
                    )
            }))
    @GetMapping("/api/review/all")
    public ResponseEntity<List<Board>> findPosts(@RequestParam(name = "userId") Long userId){

        List<Board> reviews = boardService.getAllReview(userId);

        return ResponseEntity.ok(reviews);
    }

    // 카테고리별 리스트 조회
    @Operation(summary = "카테고리별 리스트", description = "리뷰 카테고리별 리스트를 가져옵니다.")
    @GetMapping("/api/review")
    public String search(@RequestParam(name = "userId") Long userId,
                         @RequestParam(name = "category") String category
    ) {
        List<BoardRequestDTO> searchList = boardService.search(category);

        for(BoardRequestDTO board : searchList){
            BoardRequestDTO build = BoardRequestDTO.builder()
                    .title(board.getTitle())
                    .category(board.getCategory())
                    .nickname(board.getNickname())
                    .user(board.getUser())
                    .content(board.getContent())
                    .image1(board.getImage1())
                    .image2(board.getImage2())
                    .image3(board.getImage3())
                    .image4(board.getImage4())
                    .image5(board.getImage5())
                    .url(board.getUrl())
                    .score(board.getScore())
                    .build();

            searchList.add(build);
        }
        return searchList.toString();
    }



    //Read
    @Operation(summary = "리뷰 상세페이지", description = "리뷰 하나의 상세 정보를 가져옵니다.")
    @ApiResponse(responseCode = "200", description = "OK", content = @Content(
            mediaType = "application/json",
            examples = {
                    @ExampleObject(name = "true",
                            description = "리뷰 상세페이지 로딩 완료",
                            value = """
                                    true
                                    """
                    ),
                    @ExampleObject(name = "false",
                            description = "리뷰 상세피이지 로딩 실패",
                            value = """
                                    false
                                    """
                    )
            }))
    @GetMapping("/api/review/detail")
    public BoardResponseDTO findPost( @RequestParam(name = "userId") Long userId,
                                      @PathVariable("id") Long id
        ){
        BoardRequestDTO post = boardService.getPost();

        return new BoardResponseDTO(
                post.getTitle(),
                post.getCategory(),
                post.getUser(),
                post.getContent(),
                post.getImage1(),
                post.getImage2(),
                post.getImage3(),
                post.getImage3(),
                post.getImage4(),
                post.getImage5(),
                post.getUrl(),
                post.getScore()
        );
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
    public void delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
    }

}