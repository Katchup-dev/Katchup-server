package site.katchup.katchupserver.api.task.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "소분류 응답 DTO")
public class TaskGetResponseDto {

    @Schema(description = "소분류 id", example = "1")
    private Long taskId;
    @Schema(description = "소분류 이름", example = "Katchup Design")
    private String name;

    public static TaskGetResponseDto of(Long taskId, String name) {
        return new TaskGetResponseDto(taskId, name);
    }
}