package site.katchup.katchupserver.api.task.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.task.dto.TaskResponseDto;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.task.service.TaskService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final CategoryRepository categoryRepository;
    private final FolderRepository folderRepository;
    private final TaskRepository taskRepository;

    @Override
    public List<TaskResponseDto> getAllTask(Long memberId) {

        return categoryRepository.findByMemberId(memberId).stream()
                .flatMap(category -> folderRepository.findByCategoryId(category.getId()).stream())
                .flatMap(folder -> taskRepository.findByFolderId(folder.getId()).stream())
                .map(task -> TaskResponseDto.of(task.getId(), task.getName()))
                .collect(Collectors.toList());
    }
}
