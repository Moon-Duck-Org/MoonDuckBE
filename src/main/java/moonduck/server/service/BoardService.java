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

    public Board savePost(BoardRequestDTO boardDto);

    BoardRequestDTO getPost();

    public void deletePost(Long id);

    public Board update(BoardEditDTO boardDto);

    List<BoardRequestDTO> search(String category);
}