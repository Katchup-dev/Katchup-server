package site.katchup.katchupserver.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorCode;

@Getter
public class NotFoundException extends BaseException {
    public NotFoundException(ErrorCode errorStatus) {
        super(HttpStatus.NOT_FOUND, errorStatus.getCode());
    }

}
