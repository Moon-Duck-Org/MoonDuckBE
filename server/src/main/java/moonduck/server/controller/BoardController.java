package moonduck.server.controller;

import lombok.RequiredArgsConstructor;
import moonduck.server.entity.Board;
import moonduck.server.repository.BoardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    //Create 생성
    @PostMapping("/create")
    public ResponseEntity<?> createUser(Board board){
        return ResponseEntity.ok(boardRepository.save(board));
    }

    //ReadAll 리스트 페이지
    @GetMapping("/list")
    public List<Board> readAll(){
        return boardRepository.findAll();
    }

    //Read
    @GetMapping("/list/{id}")
    public Board readUser(@PathVariable Long id) throws IllegalAccessException {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalAccessException());
    }

    //Update 수정
  /*  @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, String category){
        boardRepository.findById(id)
                .map(user -> {
                    Board.setCategory(category);
                    return boardRepository.save(user);
                });
    }*/

    //Delete
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        boardRepository.deleteById(id);
    }
}
