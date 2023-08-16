package site.katchup.katchupserver.api.task.service;


import site.katchup.katchupserver.api.task.dto.request.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.request.TaskUpdateRequestDto;
import site.katchup.katchupserver.api.task.dto.response.TaskGetResponseDto;

import java.util.List;

public interface TaskService {

    //* 업무 전체 목록 조회
    List<TaskGetResponseDto> getAllTask(Long memberId);

    //* 특정 카테고리 내 업무 목록 조회
    List<TaskGetResponseDto> getByCategoryId(Long categoryId);

    //* 업무 수정
    void updateTaskName(Long taskId, TaskUpdateRequestDto taskUpdateRequestDto);

    void createTaskName(TaskCreateRequestDto taskCreateRequestDto);
    void deleteTask(Long taskId);
}
