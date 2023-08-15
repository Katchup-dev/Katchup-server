package site.katchup.katchupserver.common.exception;

import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorCode;

public class InternalServerException extends BaseException {

    public InternalServerException(ErrorCode errorStatus) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorStatus.getCode());
    }

}
