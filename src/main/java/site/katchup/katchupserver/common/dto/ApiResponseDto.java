package site.katchup.katchupserver.common.dto;

import lombok.*;
import site.katchup.katchupserver.common.response.ErrorStatus;
import site.katchup.katchupserver.common.response.SuccessStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ApiResponseDto<T> {
    private final int status;
    private final boolean success;
    private final String message;
    private T data;

    public static ApiResponseDto success(SuccessStatus status, Object data) {
        return ApiResponseDto.builder()
                .status(status.getHttpStatus().value())
                .success(true)
                .message(status.getMessage())
                .data(data)
                .build();
    }

    public static ApiResponseDto success(SuccessStatus successStatus) {
        return ApiResponseDto.builder()
                .status(successStatus.getHttpStatus().value())
                .success(true)
                .message(successStatus.getMessage())
                .build();
    }

    public static ApiResponseDto error(int status, String message) {
        return ApiResponseDto.builder()
                .status(status)
                .success(false)
                .message(message)
                .build();
    }
}

