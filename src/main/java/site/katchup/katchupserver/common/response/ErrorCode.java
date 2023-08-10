package site.katchup.katchupserver.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum ErrorCode {
    /**
     * 400 BAD_REQUEST
     */
    VALIDATION_REQUEST_MISSING_EXCEPTION("KC-101"),
    NO_TOKEN("KC-102"),
    VALIDATION_NAMING_EXCEPTION("KC-103"),
    DUPLICATE_CATEGORY_NAME("CG-104"),
    DUPLICATE_FOLDER_NAME("FD-105"),
    NOT_PDF_FILE_TYPE("CD-106"),
    FILE_SIZE_EXCEED("CD-107"),

    /**
     * 401 UNAUTHORIZED
     */
    UNAUTHORIZED_TOKEN("KC-201"),
    INVALID_MEMBER("KC-202"),
    GOOGLE_UNAUTHORIZED_USER("KC-203"),
    SIGNIN_REQUIRED("KC-204"),
    VALID_ACCESS_TOKEN("KC-205"),

    /**
     * 404 NOT_FOUND
     */
    NOT_FOUND_CATEGORY("CG-301"),
    NOT_FOUND_FOLDER("FD-302"),
    NOT_FOUND_TASK("TK-303"),
    NOT_FOUND_CARD("CD-304"),
    DELETED_CARD("CD-305"),


    /**
     * 500 SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR("KC-401"),
    FILE_UPLOAD_ERROR("CD-402"),
    IMAGE_UPLOAD_EXCEPTION("SS-403");
    
    private final String code;

}
