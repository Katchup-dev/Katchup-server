package site.katchup.katchupserver.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorStatus {
    /**
     * 400 BAD_REQUEST
     */
    VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청값이 입력되지 않았습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_MEMBER(HttpStatus.UNAUTHORIZED, "유효하지 않은 유저입니다."),
    GOOGLE_UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "구글 로그인 실패. 만료되었거나 잘못된 구글 토큰입니다."),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "존재하지 않는 중분류 폴더입니다."),

    /**
     * 500 SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 서버 에러가 발생했습니다."),
    BAD_GATEWAY_EXCEPTION(HttpStatus.BAD_GATEWAY, "일시적인 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!"),
    SERVICE_UNAVAILABLE_EXCEPTION(HttpStatus.SERVICE_UNAVAILABLE, "현재 점검 중입니다.\n잠시 후 다시 시도해주세요!"),
    IMAGE_UPLOAD_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
