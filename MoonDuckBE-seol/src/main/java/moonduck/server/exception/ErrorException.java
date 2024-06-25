package moonduck.server.exception;

import lombok.Getter;
import moonduck.server.exception.ErrorCode;

@Getter
public class ErrorException extends RuntimeException {
    private final ErrorCode errorCode;

    public ErrorException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
