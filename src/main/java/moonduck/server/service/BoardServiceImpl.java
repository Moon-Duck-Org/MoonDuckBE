package moonduck.server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.Filter;
import moonduck.server.entity.User;
import moonduck.server.exception.BoardNotFoundException;
import moonduck.server.exception.CategoryNotMatchException;
import moonduck.server.exception.UserNotFoundException;
import moonduck.server.exception.WrongFilterException;
import moonduck.server.repository.BoardRepository;
import moonduck.server.repository.BoardSearchRepository;
import moonduck.server.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Board savePost(BoardRequestDTO boardDto){
        User user = userRepository.findById(boardDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        Board board = new Board(boardDto);
        board.setUser(user);

        return boardRepository.save(board);
    }

    @Override
    public List<Board> getAllReview(Long userId, String filter) {
        if (!Filter.isOneOf(filter)) {
            throw new WrongFilterException();
        }

        return boardSearchRepository.findByUserIdWithFilter(userId, filter);
    }

    @Override
    public List<Board> getReviewWithCategory(Long userId, String category) {
        if (Category.contains(category)) {
            return boardRepository.findByUserIdAndCategory(userId, Category.valueOf(category));
        } else {
            throw new CategoryNotMatchException();
        }
    }

    @Override
    public Board getReview(Long id) {
        return boardRepository.findByIdWithUser(id)
                .orElseThrow(() -> new BoardNotFoundException());
    }

    @Transactional
    @Override
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }


    @Transactional
    @Override
    public Board update(BoardEditDTO boardDto) {
        Board board = boardRepository.findByIdWithUser(boardDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException());

        board.updateBoard(boardDto);

        return board;
    }
}