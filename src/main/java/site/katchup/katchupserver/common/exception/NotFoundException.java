package site.katchup.katchupserver.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import site.katchup.katchupserver.common.response.ErrorStatus;

@Getter
public class NotFoundException extends BaseException {
    public NotFoundException(ErrorStatus errorStatus) {
        super(HttpStatus.NOT_FOUND, errorStatus.getMessage());
    }

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
