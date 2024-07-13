package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@Transactional
@RequiredArgsConstructor
public class ShareService {

    private final BoardRepository boardRepository;

    public String getShareUrl(Long userId, Long boardId) {

        boardRepository.findByIdAndUserId(boardId, userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.BOARD_NOT_FOUND));

        return Base64.getEncoder().encodeToString(boardId.toString().getBytes());
    }
}
