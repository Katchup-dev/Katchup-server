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
    VALIDATION_EXCEPTION("잘못된 요청입니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION("요청값이 입력되지 않았습니다."),
    VALIDATION_NAMING_EXCEPTION("이모지 및 특수기호 입력은 불가능합니다. 제외하여 입력해 주세요."),
    NO_TOKEN("토큰을 넣어주세요."),
    DUPLICATE_FOLDER_NAME("해당 중분류명은 이미 존재합니다. 다른 업무명을 입력해 주세요."),
    DUPLICATE_CATEGORY_NAME("해당 대분류명은 이미 존재합니다. 다른 업무명을 입력해 주세요."),
    NOT_PDF_FILE_TYPE("PDF 파일만 업로드 가능합니다."),
    FILE_UPLOAD_ERROR("파일 업로드를 실패했습니다."),
    FILE_SIZE_EXCEED("파일 사이즈가 10MB를 초과했습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    UNAUTHORIZED_TOKEN("유효하지 않은 토큰입니다."),
    INVALID_MEMBER("유효하지 않은 유저입니다."),
    GOOGLE_UNAUTHORIZED_USER("구글 로그인 실패. 만료되었거나 잘못된 구글 토큰입니다."),
    SIGNIN_REQUIRED("access, refreshToken 모두 만료되었습니다. 재로그인이 필요합니다."),
    VALID_ACCESS_TOKEN("아직 유효한 accessToken 입니다."),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND_FOLDER("존재하지 않는 중분류 폴더입니다."),
    NOT_FOUND_CARD("존재하지 않는 업무 카드입니다."),
    NOT_FOUND_CATEGORY("존재하지 않는 대분류 카테고리입니다."),
    DELETED_CARD("이미 삭제된 업무 카드입니다."),
    INVALID_TASK("존재하지 않는 업무입니다."),

    /**
     * 500 SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR("예상치 못한 서버 에러가 발생했습니다."),
    IMAGE_UPLOAD_EXCEPTION("이미지 업로드에 실패하였습니다.");
    
    private final String message;

}
