package moonduck.server.handler;

import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import lombok.extern.slf4j.Slf4j;
import moonduck.server.exception.ErrorCode;
import moonduck.server.exception.ErrorException;
import moonduck.server.exception.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<ErrorResponse> handlerTokenException(ErrorException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        errorLogging(ex.getStackTrace(), errorCode, null);

        return ResponseEntity
                .status(HttpStatus.valueOf(errorCode.getStatus()))
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handlerDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorCode errorCode = ErrorCode.DUPLICATED_DATA;
        errorLogging(ex.getStackTrace(), errorCode, null);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(InvalidTypeIdException.class)
    public ResponseEntity<ErrorResponse> handlerInvalidTypeIdException(InvalidTypeIdException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_PROGRAM;
        errorLogging(ex.getStackTrace(), errorCode, null);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ErrorCode errorCode = ErrorCode.IMAGE_SIZE_EXCEED;
        errorLogging(ex.getStackTrace(), errorCode, null);

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ErrorCode errorCode = ErrorCode.INVALID_DATA_FORMAT;
        errorLogging(ex.getStackTrace(), errorCode, ex.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(Exception ex) {
        ErrorCode errorCode = ErrorCode.SERVER_ERROR;
        errorLogging(ex.getStackTrace(), errorCode, ex.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.of(errorCode));
    }

    private void errorLogging(StackTraceElement[] stackTrace, ErrorCode errorCode, String errorMessage) {
        String callerClassName = "Unknown";
        String callerMethodName = "Unknown";

        if (stackTrace.length > 2) {
            callerClassName = stackTrace[2].getClassName();
            callerMethodName = stackTrace[2].getMethodName();
        }

        if (errorMessage == null || errorMessage.isEmpty()) {
            log.error("\n에러 발생 위치: {}.{}\n에러 코드: {}", callerClassName, callerMethodName, errorCode);
        } else {
            log.error("\n에러 발생 위치: {}.{}\n에러 코드: {}\n에러 메세지: {}", callerClassName, callerMethodName, errorCode, errorMessage);
        }
    }
}
