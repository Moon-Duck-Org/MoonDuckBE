package moonduck.server.service;

import lombok.RequiredArgsConstructor;
import moonduck.server.dto.response.ShareDataResponse;
import moonduck.server.entity.Board;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShareService {

    private final BoardRepository boardRepository;

    public String getShareUrl(Long userId, Long boardId) {

        boardRepository.findByIdAndUserId(boardId, userId)
                .orElseThrow(() -> new ErrorException(ErrorCode.BOARD_NOT_FOUND));

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(boardId);

        String base64Url = Base64.getEncoder().encodeToString(buffer.array());

        try {
            base64Url = URLEncoder.encode(base64Url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ErrorException(ErrorCode.URL_ENCODING_FAIL);
        }

        return base64Url;
    }

    public ShareDataResponse getShareData(String base64Str) {
        try {
            base64Str = URLDecoder.decode(base64Str.trim(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        if (!Pattern.compile("^[A-Za-z0-9+/=]+$").matcher(base64Str).matches()) {
            return null;
        }

        byte[] decode = Base64.getDecoder().decode(base64Str);

        if (decode.length != Long.BYTES) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.wrap(decode);
        Long boardId = buffer.getLong();

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
