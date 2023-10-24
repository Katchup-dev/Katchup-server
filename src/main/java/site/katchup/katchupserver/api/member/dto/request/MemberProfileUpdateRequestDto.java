package site.katchup.katchupserver.api.member.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "회원 프로필 수정 요청 DTO")
public class MemberProfileUpdateRequestDto {
    @Schema(description = "회원 닉네임", example = "unan")
    @NotNull
    private String nickname;
    @NotNull
    private String introduction;
}
