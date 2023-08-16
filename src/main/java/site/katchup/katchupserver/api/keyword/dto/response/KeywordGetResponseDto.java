package site.katchup.katchupserver.api.keyword.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.keyword.domain.Keyword;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "키워드 응답 DTO")
public class KeywordGetResponseDto {
    @Schema(description = "키워드 고유 id", example = "1")
    private Long keywordId;
    @Schema(description = "키워드 이름", example = "Katchup Design")
    private String name;
    @Schema(description = "키워드 색깔", example = "#DC143C")
    private String color;

    public static KeywordGetResponseDto of(Keyword keyword) {
        return new KeywordGetResponseDto(keyword.getId(), keyword.getName(), keyword.getColor());
    }
}
