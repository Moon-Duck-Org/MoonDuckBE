package moonduck.server.exception.auth;

public class RefreshNotFoundException extends RuntimeException {
    public RefreshNotFoundException() {
        super("저장되지 않은 refresh 토큰입니다.");
    }
}
