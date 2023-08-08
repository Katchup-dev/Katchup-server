package site.katchup.katchupserver.common.advice;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.exception.BaseException;
import site.katchup.katchupserver.common.response.ErrorStatus;


@RestControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseDto> handleGlobalException(BaseException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponseDto.error(ex.getStatusCode().value(), ex.getResponseMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDto> handleMissingParameter(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(HttpStatus.BAD_REQUEST.value(),
                        ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto>  handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }

}
