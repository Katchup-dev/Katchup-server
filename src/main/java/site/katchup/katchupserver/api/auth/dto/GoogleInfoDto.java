package site.katchup.katchupserver.api.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GoogleInfoDto {

    private String nickname;

    private String email;

    private String imageUrl;
}
