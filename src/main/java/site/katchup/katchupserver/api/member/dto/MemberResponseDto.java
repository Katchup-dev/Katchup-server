package site.katchup.katchupserver.api.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "회원 응답 DTO")
public class MemberResponseDto {
    @Schema(description = "회원 고유 id", example = "1")
    private String imageUrl;
    @Schema(description = "회원 닉네임", example = "unan")
    private String nickname;
    @Schema(description = "회원 이메일", example = "katchup@katchup.com")
    private String email;
}
