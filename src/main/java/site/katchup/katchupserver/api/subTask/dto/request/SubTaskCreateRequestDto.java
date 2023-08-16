package site.katchup.katchupserver.api.subTask.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "세부 업무 생성 요청 DTO")
public class SubTaskCreateRequestDto {
    @Schema(description = "세부 업무가 속한 업무 고유 id", example = "1")
    @NotNull
    private Long folderId;
    @Schema(description = "세부 업무 이름", example = "Katchup Design")
    @NotBlank
    @Size(min=1, max=20)
    private String name;
}
