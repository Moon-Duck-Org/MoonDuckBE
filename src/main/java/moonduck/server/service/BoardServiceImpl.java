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
import moonduck.server.s3.S3Service;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;
    private final UserRepository userRepository;
    private final S3Service s3Service;

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
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException());

        List<String> images = Stream.of(board.getImage1(), board.getImage2(), board.getImage3(), board.getImage4(), board.getImage5())
                .filter(Objects::nonNull)
                .toList();

        s3Service.deleteFiles(images);

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
                if (board.getImage1() == null) {
                    board.setImage1(image);
                } else if (board.getImage2() == null) {
                    board.setImage2(image);
                } else if (board.getImage3() == null) {
                    board.setImage3(image);
                } else if (board.getImage4() == null) {
                    board.setImage4(image);
                } else if (board.getImage5() == null) {
                    board.setImage5(image);
                }
            }
        }

        return board;
    }
}