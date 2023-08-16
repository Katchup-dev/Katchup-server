package site.katchup.katchupserver.api.keyword.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "키워드 생성 요청 DTO")
public class KeywordCreateRequestDto {
    @Schema(description = "키워드 이름", example = "Katchup Design")
    @NotBlank
    private String name;
    @Schema(description = "키워드 색깔", example = "#DC143C")
    @NotBlank
    private String color;

}
