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

    public static ApiResponseDto success(SuccessStatus successStatus) {
        return ApiResponseDto.builder()
                .status(successStatus.getHttpStatus().value())
                .success(true)
                .message(successStatus.getMessage())
                .build();
    }

    public static <T> ApiResponseDto<T> success(SuccessStatus successStatus, T data) {
        return new ApiResponseDto<T>(successStatus.getHttpStatus().value(), true, successStatus.getMessage(), data);
    }

    public static ApiResponseDto error(ErrorStatus errorStatus) {
        return new ApiResponseDto<>(errorStatus.getHttpStatus().value(), false, errorStatus.getMessage());
    }
}

