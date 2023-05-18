package site.katchup.katchupserver.api.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.task.domain.Task;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByFolderId(Long folderId);
}
