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
    VALIDATION_NAMING_EXCEPTION(HttpStatus.BAD_REQUEST, "이모지 및 특수기호 입력은 불가능합니다. 제외하여 입력해 주세요."),
    NO_TOKEN(HttpStatus.BAD_REQUEST, "토큰을 넣어주세요."),
    DUPLICATE_FOLDER_NAME(HttpStatus.BAD_REQUEST, "해당 중분류명은 이미 존재합니다. 다른 업무명을 입력해 주세요."),
    DUPLICATE_CATEGORY_NAME(HttpStatus.BAD_REQUEST, "해당 대분류명은 이미 존재합니다. 다른 업무명을 입력해 주세요."),

    /**
     * 401 UNAUTHORIZED
     */
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    INVALID_MEMBER(HttpStatus.UNAUTHORIZED, "유효하지 않은 유저입니다."),
    GOOGLE_UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "구글 로그인 실패. 만료되었거나 잘못된 구글 토큰입니다."),
    SIGNIN_REQUIRED(HttpStatus.UNAUTHORIZED, "access, refreshToken 모두 만료되었습니다. 재로그인이 필요합니다."),
    VALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "아직 유효한 accessToken 입니다."),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND_FOLDER(HttpStatus.NOT_FOUND, "존재하지 않는 중분류 폴더입니다."),
    NOT_FOUND_CARD(HttpStatus.NOT_FOUND, "존재하지 않는 업무 카드입니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 대분류 카테고리입니다."),
    DELETED_CARD(HttpStatus.NOT_FOUND, "이미 삭제된 업무 카드입니다."),

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
