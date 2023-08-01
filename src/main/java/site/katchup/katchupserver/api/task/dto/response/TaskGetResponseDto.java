package site.katchup.katchupserver.api.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class TaskGetResponseDto {
    private Long taskId;

    private String name;

    public static TaskGetResponseDto of(Long taskId, String name) {
        return new TaskGetResponseDto(taskId, name);
    }
}