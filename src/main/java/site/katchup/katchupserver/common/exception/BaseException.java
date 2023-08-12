package site.katchup.katchupserver.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseException extends RuntimeException {
    HttpStatus statusCode;
    String code;

    public BaseException(HttpStatus statusCode, String code) {
        super(code);
        this.statusCode = statusCode;
        this.code = code;
    }
}
