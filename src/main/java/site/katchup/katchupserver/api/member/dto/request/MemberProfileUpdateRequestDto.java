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
    @Min(value = 2, message = "MP-117") @Max(value = 20, message = "MP-102")
    private String nickname;
    @Schema(description = "회원 한줄 소개", example = "안녕하세요")
    @Max(value = 100, message = "MP-118")
    private String introduction;
}