package site.katchup.katchupserver.api.keyword.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.keyword.domain.Keyword;

@Getter
@AllArgsConstructor(staticName = "of")
public class KeywordGetResponseDto {
    private Long keywordId;
    private String name;

    public static KeywordGetResponseDto of(Keyword keyword) {
        return new KeywordGetResponseDto(keyword.getId(), keyword.getName());
    }
}
