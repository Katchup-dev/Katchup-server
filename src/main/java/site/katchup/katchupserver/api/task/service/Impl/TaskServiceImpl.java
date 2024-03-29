package site.katchup.katchupserver.api.task.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.dto.request.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.request.TaskUpdateRequestDto;
import site.katchup.katchupserver.api.task.dto.response.TaskGetResponseDto;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.task.service.TaskService;
import site.katchup.katchupserver.common.exception.BadRequestException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskGetResponseDto> getAllByCategory(Long categoryId) {
        return taskRepository.findAllByCategoryIdAndNotDeleted(categoryId).stream()
                .map(task -> TaskGetResponseDto.of(task.getId(), task.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateTaskName(Long taskId, TaskUpdateRequestDto requestDto) {
        Task findTask = taskRepository.findByIdOrThrow(taskId);

        checkDuplicateTaskName(findTask.getCategory().getId(), requestDto.getName());

        findTask.updateTaskName(requestDto.getName());
    }

    @Override
    @Transactional
    public Long createTaskName(TaskCreateRequestDto requestDto) {
        Category findCategory = categoryRepository.findByIdOrThrow(requestDto.getCategoryId());

        checkDuplicateTaskName(findCategory.getId(), requestDto.getName());

        Task task = taskRepository.save(Task.builder()
                .name(requestDto.getName())
                .category(findCategory)
                .build());

        return task.getId();
    }

    @Override
    @Transactional
    public void deleteTask(Long taskId) {
        Task findTask = taskRepository.findByIdOrThrow(taskId);

        findTask.deleted();
        findTask.getSubTasks().forEach(this::deleteSubTaskAndCard);
    }

    private void checkDuplicateTaskName(Long categoryId, String name) {
        if (taskRepository.existsByCategoryIdAndName(categoryId, name))
            throw new BadRequestException(ErrorCode.DUPLICATE_TASK_NAME);
    }

    private void deleteSubTaskAndCard(SubTask subTask) {
        subTask.deleted();
        subTask.getCards().stream().forEach(Card::deletedCard);
    }
}
