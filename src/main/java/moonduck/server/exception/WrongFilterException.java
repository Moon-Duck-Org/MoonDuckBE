package moonduck.server.exception;

public class WrongFilterException extends RuntimeException {

    public WrongFilterException() {
        super("잘못된 필터 조건입니다.");
    }
}
