package site.katchup.katchupserver.common.response;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum SuccessStatus {
  
    /**
     * auth
     */
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    SIGNIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    GET_NEW_TOKEN_SUCCESS(HttpStatus.OK,"토큰 재발급 성공"),

    /**
     * category
     */
    READ_ALL_CATEGORY_SUCCESS(HttpStatus.OK, "대분류 카테고리 목록 조회 성공"),
    UPDATE_CATEGORY_NAME_SUCCESS(HttpStatus.OK, "업무 대분류명 수정 성공"),
    CREATE_CATEGORY_NAME_SUCCESS(HttpStatus.OK, "업무 대분류명 추가 성공"),
    DELETE_CATEGORY_SUCCESS(HttpStatus.OK, "업무 대분류 삭제 성공"),

    /**
     * folder
     */
    READ_ALL_FOLDER_SUCCESS(HttpStatus.OK, "중분류 폴더 전체 목록 조회 성공"),
    READ_BY_CATEGORY_SUCCESS(HttpStatus.OK, "특정 카테고리 내 중분류 폴더 조회 성공"),
    UPDATE_FOLDER_NAME_SUCCESS(HttpStatus.OK, "업무 중분류명 수정 성공"),
    CREATE_FOLDER_NAME_SUCCESS(HttpStatus.OK, "업무 대분류명 추가 성공"),

    /**
     * member
     */
    GET_MEMBER_SUCCESS(HttpStatus.OK, "프로필 팝업 조회 성공"),

    /**
     * task
     */
    GET_ALL_TASK_SUCCESS(HttpStatus.OK, "업무 소분류 목록 조회 성공"),
    CREATE_TASK_SUCCESS(HttpStatus.CREATED, "업무 소분류 추가 성공"),

    /**
     * card
     */
    GET_CARD_SUCCESS(HttpStatus.OK, "업무 카드 조회 성공"),
    GET_ALL_CARD_SUCCESS(HttpStatus.OK, "업무 카드 조회 목록 성공"),
    DELETE_CARD_LIST_SUCCESS(HttpStatus.OK, "업무 카드 삭제 성공"),

    /**
     * screenshot
     */
    UPLOAD_SCREENSHOT_SUCCESS(HttpStatus.CREATED, "스크린샷 추가 성공"),
    DELETE_SCREENSHOT_SUCCESS(HttpStatus.OK, "스크린샷 삭제 성공"),

    /**
     * keyword
     */
    GET_ALL_KEYWORD_SUCCESS(HttpStatus.OK, "키워드 목록 조회 성공"),
    CREATE_KEYWORD_SUCCESS(HttpStatus.CREATED, "키워드 추가 성공");

    private final HttpStatus httpStatus;
    private final String message;
}
