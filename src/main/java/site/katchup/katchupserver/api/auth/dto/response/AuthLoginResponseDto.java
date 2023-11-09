package site.katchup.katchupserver.api.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "인증/인가 응답")
public class AuthLoginResponseDto {
    @Schema(description = "닉네임", example = "unan")
    private String nickname;
    @Schema(description = "멤버 고유 id", example = "382")
    private Long memberId;
    @Schema(description = "Katchup Access Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bmFuIiwiaWF0IjoxNjI0NjQ0NjY2LCJleHAiOj")
    private String accessToken;
    @Schema(description = "Katchup Refresh Token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1bmFuIiwiaWF0IjoxNjI0NjQ0NjY2LCJleHAiOj")
    private String refreshToken;
    @Schema(description = "새로운 유저인지 여부", example = "true")
    @JsonProperty("isNewUser")
    private boolean isNewUser;
}
