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

    /**
     * category
     */
    READ_ALL_CATEGORY_SUCCESS(HttpStatus.OK, "대분류 카테고리 목록 조회 성공"),

    /**
     * member
     */
    GET_MEMBER_SUCCESS(HttpStatus.OK, "프로필 팝업 조회 성공"),

    /**
     * task
     */
    GET_ALL_TASK_SUCCESS(HttpStatus.OK, "업무 소분류 목록 조회 성공");

    private final HttpStatus httpStatus;
    private final String message;
}
