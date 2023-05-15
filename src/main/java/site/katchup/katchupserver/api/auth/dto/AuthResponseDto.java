package site.katchup.katchupserver.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponseDto {

    private String nickname;

    private String accessToken;

    private String refreshToken;

    @JsonProperty("isNewUser")
    private boolean isNewUser;
}
