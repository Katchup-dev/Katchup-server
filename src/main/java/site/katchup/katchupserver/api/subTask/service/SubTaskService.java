package site.katchup.katchupserver.api.subTask.service;

import site.katchup.katchupserver.api.subTask.dto.request.SubTaskCreateRequestDto;
import site.katchup.katchupserver.api.subTask.dto.response.SubTaskGetResponseDto;

import java.util.List;

public interface SubTaskService {

    List<SubTaskGetResponseDto> getAllSubTask(Long taskId);
    Long createSubTask(SubTaskCreateRequestDto requestDto);

}
