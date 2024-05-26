package moonduck.server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Board savePost(List<String> images, BoardRequestDTO boardDto){
        User user = userRepository.findById(boardDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        Board board = new Board(boardDto);
        board.setUser(user);

        if (images != null && !images.isEmpty()) {
            int size = images.size();

            if (size > 0) board.setImage1(images.get(0));
            if (size > 1) board.setImage2(images.get(1));
            if (size > 2) board.setImage3(images.get(2));
            if (size > 3) board.setImage4(images.get(3));
            if (size > 4) board.setImage5(images.get(4));
        }

        return boardRepository.save(board);
    }

    @Override
    public List<Board> getAllReview(Long userId, String filter) {
        if (filter != null && !Filter.isOneOf(filter)) {
            throw new WrongFilterException();
        }

        return boardSearchRepository.findByUserIdWithFilter(userId, filter);
    }

    @Override
    public List<Board> getReviewWithCategory(Long userId, String category, String filter) {
        if (filter != null && !Filter.isOneOf(filter)) {
            throw new WrongFilterException();
        }

        if (Category.contains(category)) {
            return boardSearchRepository.findByUserIdAndCategoryWithFilter(userId, Category.valueOf(category), filter);
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
    public Board update(List<String> images, BoardEditDTO boardDto) {
        Board board = boardRepository.findByIdWithUser(boardDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException());

        board.updateBoard(boardDto);

        if (images != null && !images.isEmpty()) {
            for (String image : images) {
                if (boardDto.getImage1() != null) {
                    boardDto.setImage1(image);
                } else if (boardDto.getImage2() != null) {
                    boardDto.setImage2(image);
                } else if (boardDto.getImage3() != null) {
                    boardDto.setImage3(image);
                } else if (boardDto.getImage4() != null) {
                    boardDto.setImage4(image);
                } else if (boardDto.getImage5() != null) {
                    boardDto.setImage5(image);
                }
            }
        }

        return board;
    }
}