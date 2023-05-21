package site.katchup.katchupserver.api.keyword.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeywordResponseDto {
    private Long keywordId;

    private String name;

    public static KeywordResponseDto of(Long keywordId, String name) {
        return new KeywordResponseDto(keywordId, name);
    }
}
