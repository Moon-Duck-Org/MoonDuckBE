package moonduck.server.exception;

public record ErrorResponse(
        String code,
        String message
) {
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(null, message);
    }
}
