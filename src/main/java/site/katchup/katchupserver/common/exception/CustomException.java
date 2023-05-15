package site.katchup.katchupserver.common.exception;

import lombok.Getter;
import site.katchup.katchupserver.common.response.ErrorStatus;

@Getter
public class CustomException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public CustomException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
