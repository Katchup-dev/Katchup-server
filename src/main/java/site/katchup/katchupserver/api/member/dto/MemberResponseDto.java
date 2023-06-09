package site.katchup.katchupserver.api.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberResponseDto {
    private String imageUrl;

    private String nickname;

    private String email;
}
