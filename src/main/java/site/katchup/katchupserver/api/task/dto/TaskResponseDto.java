package site.katchup.katchupserver.api.task.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;

    private String name;

    public static TaskResponseDto of(Long taskId, String name) {
        return new TaskResponseDto(taskId, name);
    }
}