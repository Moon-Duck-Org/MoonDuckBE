package moonduck.server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import moonduck.server.dto.BoardEditDTO;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.entity.Category;
import moonduck.server.entity.User;
import moonduck.server.exception.BoardNotFoundException;
import moonduck.server.exception.CategoryNotMatchException;
import moonduck.server.exception.UserNotFoundException;
import moonduck.server.repository.BoardRepository;
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
    public List<Board> getAllReview(Long userId) {
        return boardRepository.findByUserId(userId);
    }

    @Override
    public List<Board> getReviewWithCategory(Long userId, String category) {
        if (Category.contains(category)) {
            return boardRepository.findByUserIdAndCategory(userId, Category.valueOf(category));
        } else {
            throw new CategoryNotMatchException();
        }
    }

    @Transactional
    @Override
    public BoardRequestDTO getPost(){
        Optional<Board> boardWrapper = boardRepository.findById(getPost().getBoard_id());
        Board board = boardWrapper.get();

        return BoardRequestDTO.builder()
                .title(board.getTitle())
                .user(board.getUser())
                .content(board.getContent())
                .image1(board.getImage1())
                .image2(board.getImage2())
                .image3(board.getImage3())
                .image4(board.getImage4())
                .image5(board.getImage5())
                .url(board.getUrl())
                .score(board.getScore())
                .build();
    }


    @Transactional
    @Override
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }


    @Transactional
    @Override
    public Board update(BoardEditDTO boardDto) {
        Board board = boardRepository.findById(boardDto.getBoardId())
                .orElseThrow(() -> new BoardNotFoundException());

        board.setTitle(boardDto.getTitle());
        board.setCategory(boardDto.getCategory());
        board.setContent(boardDto.getContent());
        board.setImage1(boardDto.getImage1());
        board.setImage2(boardDto.getImage2());
        board.setImage3(boardDto.getImage3());
        board.setImage4(boardDto.getImage4());
        board.setImage5(boardDto.getImage5());
        board.setUrl(boardDto.getUrl());
        board.setScore(boardDto.getScore());

        return board;
    }
}