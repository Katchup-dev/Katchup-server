package site.katchup.katchupserver.api.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenGetResponseDto {
    private String accessToken;

    private String refreshToken;
}
