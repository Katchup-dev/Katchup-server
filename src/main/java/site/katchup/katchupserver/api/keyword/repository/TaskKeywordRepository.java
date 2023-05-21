package site.katchup.katchupserver.api.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.keyword.domain.TaskKeyword;

import java.util.List;

public interface TaskKeywordRepository extends JpaRepository<TaskKeyword, Long> {
    List<TaskKeyword> findByCardId(Long cardId);
}
