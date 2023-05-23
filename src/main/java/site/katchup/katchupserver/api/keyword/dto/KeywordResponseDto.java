package site.katchup.katchupserver.api.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.keyword.domain.Keyword;

@Getter
@AllArgsConstructor(staticName = "of")
public class KeywordResponseDto {
    private Long keywordId;

    private String name;

    public static KeywordResponseDto of(Keyword keyword) {
        return new KeywordResponseDto(keyword.getId(), keyword.getName());
    }
}
