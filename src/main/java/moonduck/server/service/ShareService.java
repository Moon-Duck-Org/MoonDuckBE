package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.dto.response.ShareDataResponse;
import moonduck.server.entity.Board;
import moonduck.server.entity.Share;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.repository.BoardRepository;
import moonduck.server.repository.redis.ShareRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShareService {

    private final BoardRepository boardRepository;
    private final ShareRepository shareRepository;
    private final Long DAY_MS = 24 * 60 * 60 * 1000L;

    public String getShareUrl(Long userId, Long boardId) {

        boardRepository.findByIdAndUserId(boardId, userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.BOARD_NOT_FOUND));

        String uuidStr = UUID.randomUUID().toString();
        long expirations = new Date(System.currentTimeMillis() + DAY_MS).getTime() / 1000;
        Share share = new Share(uuidStr, boardId, expirations);

        shareRepository.save(share);

        return uuidStr;
    }

    public ShareDataResponse getShareData(String param) {

        Optional<Share> share = shareRepository.findById(param);

        if (share.isEmpty()) {
            return null;
        }

        Long boardId = share.get().getBoardId();

        Optional<Board> boardOp = boardRepository.findByIdWithProgram(boardId);

        if (boardOp.isEmpty()) {
            return null;
        }

        Board board = boardOp.get();

        String category = switch (board.getCategory()) {
            case BOOK -> "책";
            case DRAMA -> "드라마";
            case MOVIE -> "영화";
            case CONCERT -> "공연";
            case TEST -> null;
        };

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 M월 d일");
        String createdAt = dateFormat.format(new Date(board.getCreatedAt().getTime()));


        return ShareDataResponse.builder()
                .title(board.getTitle())
                .category(category)
                .program(board.getProgram() == null ? null : board.getProgram())
                .content(board.getContent())
                .image(board.getImage1())
                .score(board.getScore())
                .createdAt(createdAt)
                .build();
    }
}
