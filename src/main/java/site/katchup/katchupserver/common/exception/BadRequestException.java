package site.katchup.katchupserver.common.exception;

import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorCode;

public class BadRequestException extends BaseException {

    public BadRequestException(ErrorCode errorStatus) {
        super(HttpStatus.BAD_REQUEST, errorStatus.getCode());
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
