package site.katchup.katchupserver.api.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Optional<Task> findByIdAndIsDeletedFalse(Long id);

    boolean existsByCategoryIdAndName(Long categoryId, String name);

    @Query("select t from Task t where t.category.id = :categoryId and t.isDeleted = false")
    List<Task> findAllByCategoryIdAndNotDeleted(@Param("categoryId") Long categoryId);

    default Task findByIdOrThrow(Long taskId) {
        return findByIdAndIsDeletedFalse(taskId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_TASK));
    }
}