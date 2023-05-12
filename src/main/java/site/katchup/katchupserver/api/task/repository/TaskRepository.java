package site.katchup.katchupserver.api.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.task.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
