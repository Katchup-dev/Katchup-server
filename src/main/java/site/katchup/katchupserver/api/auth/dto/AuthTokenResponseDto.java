package site.katchup.katchupserver.api.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponseDto {
    private String accessToken;

    private String refreshToken;
}
