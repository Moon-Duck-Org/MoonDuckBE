package moonduck.server.exception;

public class FileException extends RuntimeException {

    public FileException() {
        super("파일 처리 중 오류가 발생했습니다.");
    }
}
