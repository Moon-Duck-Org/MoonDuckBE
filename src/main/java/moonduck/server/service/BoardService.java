package moonduck.server.service;

import jakarta.transaction.Transactional;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    public Board savePost(BoardRequestDTO boardDto);

    @Transactional
    List<BoardRequestDTO> getBoardList();

    @Transactional
    public BoardRequestDTO getPost(Long id);

    @Transactional
    BoardRequestDTO getPost();

    public void deletePost(Long id);

    public void update(Long id, Category category, BoardRequestDTO dto);

    List<BoardRequestDTO> search(String category);
}