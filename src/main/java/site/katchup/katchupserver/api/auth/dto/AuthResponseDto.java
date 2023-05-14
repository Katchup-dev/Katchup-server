package site.katchup.katchupserver.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class AuthResponseDto {
    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("is_new_user")
    public boolean isNewUser;
}
