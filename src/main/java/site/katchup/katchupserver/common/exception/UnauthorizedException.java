package site.katchup.katchupserver.common.exception;

import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorCode;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(ErrorCode errorStatus) {
        super(HttpStatus.UNAUTHORIZED, errorStatus.getCode());
    }

}
