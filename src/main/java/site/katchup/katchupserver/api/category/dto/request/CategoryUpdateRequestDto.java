package site.katchup.katchupserver.api.category.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "카테고리 수정 요청 DTO")
public class CategoryUpdateRequestDto {
    @Schema(description = "카테고리 이름", example = "Katchup Design")
    @Pattern(regexp = "^[\\p{L}\\p{N}\\s]+$", message = "KC-103")
    @NotBlank(message = "CG-108")
    private String name;
}
