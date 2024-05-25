package moonduck.server.exception;

public class BoardNotFoundException extends RuntimeException {
    public BoardNotFoundException() {
        super("존재하지 않는 리뷰입니다.");
    }
}
