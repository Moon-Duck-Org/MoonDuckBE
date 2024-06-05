package moonduck.server.exception.auth;

import lombok.Getter;
import moonduck.server.exception.ErrorCode;

@Getter
public class TokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public TokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
