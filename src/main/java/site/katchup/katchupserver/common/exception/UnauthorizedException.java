package site.katchup.katchupserver.common.exception;

import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorStatus;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(ErrorStatus errorStatus) {
        super(HttpStatus.UNAUTHORIZED, errorStatus.getMessage());
    }

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
