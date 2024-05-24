package moonduck.server.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import moonduck.server.dto.BoardRequestDTO;
import moonduck.server.entity.Board;
import moonduck.server.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void savePost(BoardRequestDTO boardDto){
        boardRepository.save(boardDto.ToEntity());
    }


    @Transactional
    @Override
    public List<BoardRequestDTO> getBoardList(){

        List<Board> all = boardRepository.findAll();
        List<BoardRequestDTO> boardDtoList = new ArrayList<>();

        for(Board board : all){
            BoardRequestDTO boardDto = BoardRequestDTO.builder()
                    .title(board.getTitle())
                    .nickname(board.getNickname())
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

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    @Transactional
    @Override
    public BoardRequestDTO getPost(Long id){
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        return BoardRequestDTO.builder()
                .title(board.getTitle())
                .nickname(board.getNickname())
              //  .user(board.getUser())
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
    public void update(Long id, BoardRequestDTO dto) {
        Optional<Board> byId = boardRepository.findById(id);
        Board board = byId.get();

        board.updateBoard(dto.getTitle(), dto.getCategory(), dto.getNickname(),
                dto.getUser(), dto.getContent(), dto.getImage1(), dto.getImage2(), dto.getImage3(), dto.getImage4(), dto.getImage5(),
                dto.getUrl(), dto.getScore());
    }

}