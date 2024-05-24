package moonduck.server.exception;

public class NicknameDuplicateException extends RuntimeException {

    public NicknameDuplicateException() {
        super("중복된 닉네임입니다.");
    }
}
