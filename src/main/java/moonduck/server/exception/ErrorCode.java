package moonduck.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    BOARD_NOT_FOUND(400, "존재하지 않는 리뷰입니다."),
    CATEGORY_NOT_MATCH(400, "잘못된 카테고리입니다."),


    USER_NOT_FOUND(401, "존재하지 않는 유저입니다."),
    NICKNAME_DUPLICATE(400, "중복된 닉네임입니다."),


    WRONG_FILTER(400, "잘못된 필터 조건입니다."),
    FILE_ERROR(500, "파일 처리 중 오류가 발생했습니다."),


    NO_TOKEN(400, "토큰이 존재하지 않습니다."),
    NOT_MATCH_CATEGORY(400, "잘못된 유형의 토큰입니다."),
    TOKEN_EXPIRED(403, "만료된 토큰입니다."),
    TOKEN_NOT_EXPIRED(403, "아직 토큰이 만료되지 않았습니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    UNAUTHENTICATED_USER(401, "인증정보가 등록되지 않았습니다. 서버에 문의해주세요.");

    private final int status;
    private final String message;
}
