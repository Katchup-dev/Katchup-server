package site.katchup.katchupserver.api.subTask.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "세부 업무 응답 DTO")
public class SubTaskGetResponseDto {

    @Schema(description = "세부 업무 id", example = "1")
    private Long subTaskId;
    @Schema(description = "세부 업무 이름", example = "Katchup Design")
    private String name;

    public static SubTaskGetResponseDto of(Long taskId, String name) {
        return new SubTaskGetResponseDto(taskId, name);
    }
}