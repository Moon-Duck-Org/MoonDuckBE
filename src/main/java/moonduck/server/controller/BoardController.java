package moonduck.server.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.repository.BoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static moonduck.server.entity.Board.*;

@RestController
@RequestMapping("/Board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    //Create 생성
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.", tags = {"Board"})
    @PostMapping("/create")
    public ResponseEntity<?> createBoard(Board board){
        return ResponseEntity.ok(boardRepository.save(board));
    }

    //ReadAll 리스트 페이지
    @Operation(summary = "리뷰 리스트", description = "리뷰 리스트를 가져옵니다.", tags = {"Board"})
    @GetMapping("/list")
    public List<Board> readAll(){
        return boardRepository.findAll();
    }

    //Read
    @Operation(summary = "리뷰 디테일", description = "리뷰 하나의 상세 정보를 가져옵니다.", tags = {"Board"})
    @GetMapping("/list/{id}")
    public Board readBoard(@PathVariable Long id) throws IllegalAccessException {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessException());
    }

    //Update 수정
  /*  @PutMapping("/{id}")
    public void updateBoard(@PathVariable Long id, Category category){
        boardRepository.findById(id)
                .map(user -> {
                    Board.getCategory(category);
                    return boardRepository.save(user);
                });
    }*/

    //Delete
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.", tags = {"Board"})
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
    }
}
