package site.katchup.katchupserver.api.subTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    @Query("select s from SubTask s where s.task.id = :taskId and s.isDeleted = false")
    List<SubTask> findAllByTaskIdAndNotDeleted(@Param("taskId") Long taskId);

    Optional<SubTask> findSubTaskByTaskAndName(Task task, String name);

    default SubTask findByIdOrThrow(Long subTaskId) {
        return findById(subTaskId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_SUB_TASK));
    }

    default SubTask findOrCreateEtcSubTask(Task task, String etcName) {
        return findSubTaskByTaskAndName(task, etcName)
                .orElseGet(() -> {
                    SubTask etcSubTask = SubTask.builder()
                            .task(task)
                            .name(etcName)
                            .build();
                    return save(etcSubTask);
                });
    }
}
