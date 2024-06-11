package moonduck.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.dto.program.*;
import moonduck.server.dto.request.BoardEditRequest;
import moonduck.server.dto.request.BoardRequest;
import moonduck.server.dto.response.BoardResponse;
import moonduck.server.entity.Board;
import moonduck.server.entity.program.*;
import moonduck.server.enums.Category;
import moonduck.server.enums.Filter;
import moonduck.server.entity.User;
import moonduck.server.exception.*;
import moonduck.server.repository.BoardRepository;
import moonduck.server.repository.BoardSearchRepository;
import moonduck.server.repository.ProgramRepository;
import moonduck.server.repository.UserRepository;
import moonduck.server.service.s3.S3Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardSearchRepository boardSearchRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;
    private final S3Service s3Service;

    private final ObjectMapper objectMapper;

    @Transactional
    public BoardResponse savePost(List<String> images, BoardRequest boardDto, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.USER_NOT_FOUND));

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

        String category = boardDto.getCategory().toString();
        if (!Category.contains(category)) {
            throw new ErrorException(ErrorCode.CATEGORY_NOT_MATCH);
        }

        if (boardDto.getProgram() != null) {
            Program program = getProgram(category, boardDto.getProgram());

            program = programRepository.save(program);
            board.setProgram(program);
        }

        Board savedBoard = boardRepository.save(board);

        BoardResponse boardResponse = BoardResponse.from(savedBoard);

        return boardResponse;
    }

    private Program getProgram(String category, ProgramDTO programDto) {
        Program program = null;

        switch (category) {
            case "MOVIE":
                MovieDTO movieDTO = objectMapper.convertValue(programDto, MovieDTO.class);
                program = new Movie(movieDTO);
                break;
            case "BOOK":
                BookDTO bookDTO = objectMapper.convertValue(programDto, BookDTO.class);
                program = new Book(bookDTO);
                break;
            case "DRAMA":
                DramaDTO dramaDTO = objectMapper.convertValue(programDto, DramaDTO.class);
                program = new Drama(dramaDTO);
                break;
            case "CONCERT":
                ConcertDTO concertDTO = objectMapper.convertValue(programDto, ConcertDTO.class);
                program = new Concert(concertDTO);
                break;
            default:
                throw new ErrorException(ErrorCode.INVALID_PROGRAM);
        }

        return program;
    }

    public Page<BoardResponse> getAllReview(Long userId, String filter, Pageable pageable) {
        if (filter != null && !Filter.isOneOf(filter)) {
            throw new ErrorException(ErrorCode.WRONG_FILTER);
        }

        Page<Board> boards = boardSearchRepository.findByUserIdWithFilter(userId, filter, pageable);

        return boards.map(BoardResponse::from);
    }

    public Page<BoardResponse> getReviewWithCategory(Long userId, String category, String filter, Pageable pageable) {
        if (filter != null && !Filter.isOneOf(filter)) {
            throw new ErrorException(ErrorCode.WRONG_FILTER);
        }

        if (Category.contains(category)) {
            Page<Board> boards = boardSearchRepository.findByUserIdAndCategoryWithFilter(userId, Category.valueOf(category), filter, pageable);

            return boards.map(BoardResponse::from);
        } else {
            throw new ErrorException(ErrorCode.CATEGORY_NOT_MATCH);
        }
    }

    public BoardResponse getReview(Long id) {
        Board board = boardRepository.findByIdWithUser(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.BOARD_NOT_FOUND));

        BoardResponse boardResponse = BoardResponse.from(board);

        return boardResponse;
    }

    @Transactional
    public void deletePost(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.BOARD_NOT_FOUND));

        List<String> images = Stream.of(board.getImage1(), board.getImage2(), board.getImage3(), board.getImage4(), board.getImage5())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        s3Service.deleteFiles(images);

        boardRepository.deleteById(id);
    }


    @Transactional
    public BoardResponse update(List<String> images, BoardEditRequest boardDto) {
        Board board = boardRepository.findByIdWithUser(boardDto.getBoardId())
                .orElseThrow(() -> new ErrorException(ErrorCode.BOARD_NOT_FOUND));

        // 삭제 대상 이미지를 deleteTargetImages에 담기
        Set<String> saveImages = Stream.of(boardDto.getImage1(), boardDto.getImage2(), boardDto.getImage3(), boardDto.getImage4(), boardDto.getImage5())
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<String> deleteTargetImages = Stream.of(board.getImage1(), board.getImage2(), board.getImage3(), board.getImage4(), board.getImage5())
                .filter(Objects::nonNull)
                .filter((target) -> !saveImages.contains(target))
                .collect(Collectors.toList());

        s3Service.deleteFiles(deleteTargetImages);

        board.updateBoard(boardDto);

        // 비어있는 객체는 들어갈 수 있지만, null값이 들어가면 안됨
        List<String> saveTargetImages = Stream.concat(
                        saveImages.stream(),
                        images.stream()
                )
                .collect(Collectors.toList());

        board.setImage1(null);
        board.setImage2(null);
        board.setImage3(null);
        board.setImage4(null);
        board.setImage5(null);

        if (!saveTargetImages.isEmpty()) {
            for (String image : saveTargetImages) {
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
                } else {
                    s3Service.deleteFile(image);
                }
            }
        }

        BoardResponse boardResponse = BoardResponse.from(board);

        return boardResponse;
    }
}