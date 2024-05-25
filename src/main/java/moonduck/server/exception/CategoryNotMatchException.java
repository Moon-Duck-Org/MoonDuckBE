package moonduck.server.exception;

public class CategoryNotMatchException extends RuntimeException {

    public CategoryNotMatchException() {
        super("잘못된 카테고리입니다.");
    }
}
