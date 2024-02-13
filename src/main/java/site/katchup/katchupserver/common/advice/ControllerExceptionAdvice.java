package site.katchup.katchupserver.common.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.exception.BaseException;
import site.katchup.katchupserver.common.exception.InternalServerException;
import site.katchup.katchupserver.common.response.ErrorCode;
import site.katchup.katchupserver.external.slack.SlackService;

import static site.katchup.katchupserver.common.response.ErrorCode.VALIDATION_REQUEST_MISSING_EXCEPTION;


@RestControllerAdvice
public class ControllerExceptionAdvice {

    private final String channel;

    public ControllerExceptionAdvice(@Value("${slack.channel}") final String channel, SlackService slackService) {
        this.channel = channel;
        this.slackService = slackService;
    }

    private final SlackService slackService;

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponseDto> handleGlobalException(BaseException ex) {
        if (ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            slackService.sendMessage(channel, ex.getMessage());
        }
        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponseDto.error(ex.getCode()));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiResponseDto> handleInternalServerException(InternalServerException ex) {
        slackService.sendMessage(channel, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponseDto> handleMissingParameter(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(VALIDATION_REQUEST_MISSING_EXCEPTION.getCode()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto>  handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto>  handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(ex.getFieldError().getDefaultMessage()));
    }

}
