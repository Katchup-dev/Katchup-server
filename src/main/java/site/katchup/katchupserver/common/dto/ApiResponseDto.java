package site.katchup.katchupserver.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponseDto<T> {

    private static final String SUCCESS_CODE = "SSS";

    private final String status;
    private T data;

    public static ApiResponseDto success(Object data) {
        return ApiResponseDto.builder()
                .status(SUCCESS_CODE)
                .data(data)
                .build();
    }

    public static ApiResponseDto success() {
        return ApiResponseDto.builder()
                .status(SUCCESS_CODE)
                .build();
    }

    public static ApiResponseDto error(String errorCode) {
        return ApiResponseDto.builder()
                .status(errorCode)
                .build();
    }
}

