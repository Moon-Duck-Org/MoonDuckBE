package moonduck.server.service;

import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.item.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    Board savePost(List<String> images, BoardRequestDTO boardDto);

    List<Board> getAllReview(Long userId, String filter);

    Board getReview(Long id);

    void deletePost(Long id);

    Board update(List<String> images, BoardEditDTO boardDto);

    List<Board> getReviewWithCategory(Long userId, String category, String filter);
}