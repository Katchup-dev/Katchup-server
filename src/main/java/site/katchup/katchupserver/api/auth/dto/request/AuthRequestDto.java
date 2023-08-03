package site.katchup.katchupserver.api.auth.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
public class AuthRequestDto {

    private String accessToken;
}
