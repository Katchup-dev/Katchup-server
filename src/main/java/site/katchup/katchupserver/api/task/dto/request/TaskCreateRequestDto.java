package site.katchup.katchupserver.api.task.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "소분류 업무 생성 요청 DTO")
public class TaskCreateRequestDto {
    @Schema(description = "소분류 업무가 속한 중분류 고유 id", example = "1")
    @NotNull
    private Long folderId;
    @Schema(description = "소분류 업무 이름", example = "Katchup Design")
    @NotBlank
    @Size(min=1, max=20)
    private String name;
}
