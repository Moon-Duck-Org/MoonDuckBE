package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.dto.BoardResponseDTO;
import moonduck.server.entity.Board;
import moonduck.server.repository.BoardRepository;
import moonduck.server.service.BoardServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardServiceImpl boardService;
    private final BoardRepository boardRepository;

    //Create 생성
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.", tags = {"Board"})
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

    //ReadAll 리스트 페이지
    @Operation(summary = "리뷰 전체 리스트", description = "리뷰 전체 리스트를 가져옵니다.", tags = {"Board"})
    @GetMapping("/list")
    public List<Board> readAll(){
        return boardRepository.findAll();
    }

    //Update 수정
    @PutMapping("/api/post/{id}")
    public BoardResponseDTO updatePost(@PathVariable("id") Long id,
                                       @RequestBody @Valid BoardRequestDTO request) {

        boardService.update(id, request);
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
                board.getScore());

                return boardResponseDTO;
    }

    //Read
    @GetMapping("/api/board/posts")
    public List<BoardRequestDTO> findPosts(){
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
    @Operation(summary = "리뷰 디테일", description = "리뷰 하나의 상세 정보를 가져옵니다.", tags = {"Board"})
    @GetMapping("/api/board/post/{id}")
    public BoardResponseDTO findPost(@PathVariable("id") Long id, BoardRequestDTO request){
        BoardRequestDTO post = boardService.getPost(id);

        return new BoardResponseDTO(
                post.getNickname(),
                post.getCategory(),
                post.getTitle(),
                post.getUser(),
                post.getContent(),
                post.getImage1(),
                post.getImage2(),
                post.getImage3(),
                post.getImage3(),
                post.getImage4(),
                post.getImage5(),
                post.getUrl(),
                post.getScore());
    }


    //Delete
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.", tags = {"Board"})
    @DeleteMapping("/api/post/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
    }

}