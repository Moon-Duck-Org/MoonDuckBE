package moonduck.server.service;

import jakarta.transaction.Transactional;
import moonduck.server.dto.BoardRequestDTO;

import java.util.List;

public interface BoardService {

    public void savePost(BoardRequestDTO boardDto);

    @Transactional
    List<BoardRequestDTO> getBoardList();

    public BoardRequestDTO getPost(Long id);

    public void deletePost(Long id);

    public void update(Long id, BoardRequestDTO dto);
}