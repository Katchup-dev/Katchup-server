package site.katchup.katchupserver.api.task.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.dto.request.TaskCreateRequestDto;
import site.katchup.katchupserver.api.task.dto.response.TaskGetResponseDto;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.task.service.TaskService;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final CategoryRepository categoryRepository;
    private final FolderRepository folderRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public List<TaskGetResponseDto> getAllTask(Long memberId) {

        return categoryRepository.findByMemberId(memberId).stream()
                .flatMap(category -> folderRepository.findByCategoryId(category.getId()).stream())
                .flatMap(folder -> taskRepository.findByFolderId(folder.getId()).stream())
                .map(task -> TaskGetResponseDto.of(task.getId(), task.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createTask(TaskCreateRequestDto requestDto) {
        Folder folder = folderRepository.findById(requestDto.getFolderId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_FOLDER));

        Task task = new Task(requestDto.getName(), folder);

        taskRepository.save(task);
    }
}
