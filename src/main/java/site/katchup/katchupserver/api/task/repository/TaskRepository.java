package site.katchup.katchupserver.api.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByFolderId(Long folderId);

    default Task findByIdOrThrow(Long taskId) {
        return findById(taskId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_TASK));
    }
}
