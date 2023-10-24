package site.katchup.katchupserver.api.member.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.katchup.katchupserver.api.member.domain.Member;

@Data
@Schema(description = "회원 응답 DTO")
public class MemberProfileGetResponseDto {

    @Schema(description = "회원 프로필 이미지 url", example = "1")
    private String imageUrl;
    @Schema(description = "회원 닉네임", example = "unan")
    private String nickname;
    @Schema(description = "회원 이메일", example = "katchup@katchup.com")
    private String email;

    @Builder
    public MemberProfileGetResponseDto(String imageUrl, String nickname, String email) {
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.email = email;
    }

    public static MemberProfileGetResponseDto of(Member member) {
        return MemberProfileGetResponseDto.builder()
                .imageUrl(member.getMemberProfile().getImageUrl())
                .nickname(member.getMemberProfile().getNickname())
                .email(member.getEmail())
                .build();
    }
}
