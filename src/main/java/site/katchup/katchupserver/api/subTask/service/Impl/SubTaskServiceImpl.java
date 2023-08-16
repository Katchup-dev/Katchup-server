package site.katchup.katchupserver.api.subTask.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.subTask.dto.response.SubTaskGetResponseDto;
import site.katchup.katchupserver.api.subTask.repository.SubTaskRepository;
import site.katchup.katchupserver.api.subTask.service.SubTaskService;
import site.katchup.katchupserver.api.subTask.dto.request.SubTaskCreateRequestDto;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubTaskServiceImpl implements SubTaskService {

    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;

    @Override
    @Transactional
    public List<SubTaskGetResponseDto> getAllSubTask(Long taskId) {

        return subTaskRepository.findByTaskId(taskId).stream()
                .map(subTask -> SubTaskGetResponseDto.of(subTask.getId(), subTask.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createSubTask(SubTaskCreateRequestDto requestDto) {
        Task task = taskRepository.findByIdOrThrow(requestDto.getFolderId());

        SubTask subTask = new SubTask(requestDto.getName(), task);

        subTaskRepository.save(subTask);
    }
}
