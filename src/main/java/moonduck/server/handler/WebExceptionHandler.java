package moonduck.server.handler;

import moonduck.server.exception.*;
import moonduck.server.exception.ErrorException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<String> handlerTokenException(ErrorException ex, WebRequest request) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(errorCode.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
