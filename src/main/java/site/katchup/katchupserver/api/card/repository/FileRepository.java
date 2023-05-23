package site.katchup.katchupserver.api.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.card.domain.File;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByCardId(Long cardId);
}
