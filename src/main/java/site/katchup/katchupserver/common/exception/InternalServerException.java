package site.katchup.katchupserver.common.exception;

import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorStatus;

public class InternalServerException extends BaseException {

    public InternalServerException(ErrorStatus errorStatus) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorStatus.getMessage());
    }

    public InternalServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

}
