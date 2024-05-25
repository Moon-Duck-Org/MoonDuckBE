package moonduck.server.service;

import jakarta.transaction.Transactional;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    public void savePost(BoardRequestDTO boardDto);

    @Transactional
    List<BoardRequestDTO> getBoardList();

    @Transactional
    public BoardRequestDTO getPost(Board user, Category category);

    public void deletePost(Long id);

    public void update(Long id, Category category, BoardRequestDTO dto);
}