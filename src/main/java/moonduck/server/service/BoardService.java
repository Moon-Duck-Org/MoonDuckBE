package moonduck.server.service;

import jakarta.transaction.Transactional;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    public void savePost(BoardRequestDTO boardDto);

    @Transactional
    List<BoardRequestDTO> getBoardList();

    public BoardRequestDTO getPost(Long id);

    public void deletePost(Long id);

    public void update(Long id, Category category, BoardRequestDTO dto);
}