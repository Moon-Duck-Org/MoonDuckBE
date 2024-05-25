package moonduck.server.service;

import jakarta.transaction.Transactional;
import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    Board savePost(BoardRequestDTO boardDto);

    List<Board> getAllReview(Long userId);

    BoardRequestDTO getPost();

    void deletePost(Long id);

    Board update(BoardEditDTO boardDto);

    List<BoardRequestDTO> search(String category);
}