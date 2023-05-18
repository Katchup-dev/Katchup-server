package site.katchup.katchupserver.api.task.service;

import site.katchup.katchupserver.api.task.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {
    List<TaskResponseDto> getAllTask(Long memberId);
}
