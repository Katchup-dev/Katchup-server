package site.katchup.katchupserver.common.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.ErrorStatus;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 BAD_REQUEST
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponseDto handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiResponseDto.error(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION);
    }

    /**
     * 500 INTERNAL_SERVER_ERROR
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponseDto unhandledExceptionHandler(final Exception e) {
        return ApiResponseDto.error(ErrorStatus.INTERNAL_SERVER_ERROR);
    }
}
