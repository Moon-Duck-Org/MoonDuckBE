package moonduck.server.service;

import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BoardService {

    Board savePost(List<String> images, BoardRequestDTO boardDto);

    Page<Board> getAllReview(Long userId, String filter, Pageable pageable);

    Board getReview(Long id);

    void deletePost(Long id);

    Board update(List<String> images, BoardEditDTO boardDto);

    List<Board> getReviewWithCategory(Long userId, String category, String filter);
}