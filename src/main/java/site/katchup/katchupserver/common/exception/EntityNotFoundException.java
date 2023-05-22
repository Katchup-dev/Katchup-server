package site.katchup.katchupserver.common.exception;

import lombok.Getter;
import site.katchup.katchupserver.common.response.ErrorStatus;

@Getter
public class EntityNotFoundException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public EntityNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}
