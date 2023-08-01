package site.katchup.katchupserver.api.task.service;

import site.katchup.katchupserver.api.task.dto.request.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.response.TaskGetResponseDto;

import java.util.List;

public interface TaskService {
    List<TaskGetResponseDto> getAllTask(Long memberId);
    void createTask(TaskCreateRequestDto requestDto);
}
