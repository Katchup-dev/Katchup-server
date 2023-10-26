package site.katchup.katchupserver.api.member.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "회원 프로필 수정 요청 DTO")
public class MemberProfileUpdateRequestDto {
    @Schema(description = "회원 닉네임", example = "unan")
    @Min(2) @Max(20)
    private String nickname;
    @Schema(description = "회원 한줄 소개", example = "안녕하세요")
    @Max(100)
    private String introduction;

}
