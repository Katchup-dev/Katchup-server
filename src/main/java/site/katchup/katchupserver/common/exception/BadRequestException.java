package site.katchup.katchupserver.common.exception;

import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(ErrorStatus errorStatus) {
        super(HttpStatus.BAD_REQUEST, errorStatus.getMessage());
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

}
