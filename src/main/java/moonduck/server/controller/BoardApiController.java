package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.dto.BoardResponseDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;
import moonduck.server.repository.BoardRepository;
import moonduck.server.service.BoardServiceImpl;
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
    @PostMapping("/api/post")
    public BoardResponseDTO savePost(@RequestBody @Valid BoardRequestDTO request) {

        boardService.savePost(request);

        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(
                request.ToEntity().getTitle(),
                request.ToEntity().getCategory(),
                request.ToEntity().getNickname(),
                request.ToEntity().getUser(),
                request.ToEntity().getContent(),
                request.ToEntity().getImage1(),
                request.ToEntity().getImage2(),
                request.ToEntity().getImage3(),
                request.ToEntity().getImage3(),
                request.ToEntity().getImage4(),
                request.ToEntity().getImage5(),
                request.ToEntity().getUrl(),
                request.ToEntity().getScore()
        );
        return boardResponseDTO;
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
    @PutMapping("/api/post/{id},{category}")
    public BoardResponseDTO updatePost(@PathVariable("id") Long id,
                                       @PathVariable("category") Category category,
                                       @RequestBody @Valid BoardRequestDTO request) {

        boardService.update(id, category,request);
        Optional<Board> findPost = boardRepository.findById(id);
        Board board = findPost.get();

        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(
                board.getTitle(),
                board.getCategory(),
                board.getNickname(),
                board.getUser(),
                board.getContent(),
                board.getImage1(),
                board.getImage2(),
                board.getImage3(),
                board.getImage3(),
                board.getImage4(),
                board.getImage5(),
                board.getUrl(),
                board.getScore()
        );

                return boardResponseDTO;
    }

    //Read
    @Operation(summary = "리뷰 전체 리스트", description = "리뷰 전체 리스트를 가져옵니다.")
    @GetMapping("/api/board/posts/{user}")
    public List<BoardRequestDTO> findPosts(BoardRequestDTO boardRequestDTO){
        List<Board> findAll = boardRepository.findAll();
        List<BoardRequestDTO> allPost = new ArrayList<>();

        for(Board board : findAll){
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

            allPost.add(build);
        }

        return allPost;
    }


    //Read
    @Operation(summary = "리뷰 상세페이지", description = "리뷰 하나의 상세 정보를 가져옵니다.")
    @GetMapping("/api/board/post/{id}")
    public BoardResponseDTO findPost(@PathVariable("user") User user, @PathVariable("id") Long id, BoardRequestDTO request){
        BoardRequestDTO post = boardService.getPost(id);

        return new BoardResponseDTO(
                        post.getTitle(),
                        post.getCategory(),
                        post.getNickname(),
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
    @DeleteMapping("/api/post/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
    }

}