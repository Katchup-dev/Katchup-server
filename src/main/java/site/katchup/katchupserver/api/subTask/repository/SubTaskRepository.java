package site.katchup.katchupserver.api.subTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {
    List<SubTask> findAllByTaskId(Long taskId);

    default SubTask findByIdOrThrow(Long subTaskId) {
        return findById(subTaskId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_SUB_TASK));
    }
}
