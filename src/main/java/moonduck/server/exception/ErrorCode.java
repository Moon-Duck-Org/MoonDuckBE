package moonduck.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NO_TOKEN(400, "토큰이 존재하지 않습니다."),
    NOT_MATCH_CATEGORY(400, "잘못된 유형의 토큰입니다."),
    TOKEN_EXPIRED(403, "만료된 토큰입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다.");

    private final int status;
    private final String message;
}
