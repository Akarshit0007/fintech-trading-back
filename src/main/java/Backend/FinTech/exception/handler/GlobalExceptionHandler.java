package Backend.FinTech.exception.handler;

import Backend.FinTech.enums.ErrorCode;
import Backend.FinTech.exception.response.ErrorResponse;
import Backend.FinTech.exception.base.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex,
                                                             HttpServletRequest request) {
        ErrorCode code = ex.getErrorCode();
        ErrorResponse errorResponse =  ErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .status(code.getStatus())
                .errorCode(code.getErrorCode())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(code.getStatus()));
    }

}
