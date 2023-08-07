package site.katchup.katchupserver.api.keyword.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.keyword.domain.Keyword;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "키워드 응답 DTO")
public class KeywordResponseDto {
    @Schema(description = "키워드 고유 id", example = "1")
    private Long keywordId;
    @Schema(description = "키워드 이름", example = "Katchup Design")
    private String name;

    public static KeywordResponseDto of(Keyword keyword) {
        return new KeywordResponseDto(keyword.getId(), keyword.getName());
    }
}
