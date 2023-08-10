package site.katchup.katchupserver.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResponseDto<T> {
    private final String status;
    private T data;

    public static ApiResponseDto success(Object data) {
        return ApiResponseDto.builder()
                .status("SSS")
                .data(data)
                .build();
    }

    public static ApiResponseDto success() {
        return ApiResponseDto.builder()
                .status("SSS")
                .build();
    }

    public static ApiResponseDto error(String errorCode) {
        return ApiResponseDto.builder()
                .status(errorCode)
                .build();
    }
}

