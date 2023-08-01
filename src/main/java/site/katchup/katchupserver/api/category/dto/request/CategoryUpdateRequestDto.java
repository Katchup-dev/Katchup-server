package site.katchup.katchupserver.api.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "대분류 수정 요청 DTO")
public class CategoryUpdateRequestDto {
    @Schema(description = "대분류 이름", example = "Katchup Design")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_\\s]*$", message = "이모지 및 특수기호 입력은 불가능합니다. 제외하여 입력해 주세요.")
    private String name;
}
