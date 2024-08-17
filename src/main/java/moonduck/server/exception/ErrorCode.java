package moonduck.server.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 보드 에러
     */
    BOARD_NOT_FOUND(400, "BO001", "존재하지 않는 리뷰입니다."),
    CATEGORY_NOT_MATCH(400, "BO002", "잘못된 카테고리입니다."),
    WRONG_FILTER(400, "BO003", "잘못된 필터 조건입니다."),
    FILE_ERROR(500, "BO004", "파일 처리 중 오류가 발생했습니다."),
    INVALID_PROGRAM(400, "BO005", "유효하지 않은 program입니다. category 필드와 program_type 필드를 확인해주세요."),
    IMAGE_SIZE_EXCEED(400, "BO006", "이미지 용량이 초과됐습니다. 용량을 확인해주세요."),
    URL_ENCODING_FAIL(400, "BO007", "유효하지 않은 링크입니다."),

    /**
     * 유저 에러
     */
    USER_NOT_FOUND(401, "US001", "존재하지 않는 유저입니다."),
    NICKNAME_DUPLICATE(400, "US002", "중복된 닉네임입니다."),

    /**
     * 인증 에러
     */
    NO_TOKEN(400, "AU001", "토큰이 존재하지 않습니다."),
    NOT_MATCH_CATEGORY(400, "AU002", "잘못된 유형의 토큰입니다."),
    TOKEN_EXPIRED(403, "AU003", "만료된 토큰입니다."),
    TOKEN_NOT_EXPIRED(403, "AU004", "아직 토큰이 만료되지 않았습니다."),
    INVALID_TOKEN(401, "AU005", "유효하지 않은 토큰입니다."),
    UNAUTHENTICATED_USER(401, "AU006", "인증정보가 등록되지 않았습니다. 서버에 문의해주세요."),
    FAIL_CREATE_REVOKE_TOKEN(500, "AU007", "revoke Token 생성에 실패했습니다."),
    INVALID_REFRESH_TOKEN(401, "AU008", "유효하지 않은 refresh token입니다. 재로그인 해주세요."),

    /**
     * 서버 에러
     */
    SERVER_ERROR(500, "SY001", "알 수 없는 에러가 발생했습니다. 서버에 문의해주세요."),
    INVALID_DATA_FORMAT(400, "SY002", "잘못된 요청 데이터입니다."),
    DUPLICATED_DATA(409, "SY003", "데이터 중복이 발생했습니다. 서버에 문의해주세요.");

    private final int status;
    private final String code;
    private final String message;
}
