package site.katchup.katchupserver.common.exception;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {

    /*
    user
    */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입이 완료되었습니다."),
    SIGNIN_SUCCESS(HttpStatus.OK, "로그인을 성공하였습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
