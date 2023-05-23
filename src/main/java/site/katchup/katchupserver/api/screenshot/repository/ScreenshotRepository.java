package site.katchup.katchupserver.api.screenshot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;

import java.util.List;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {

    List<Screenshot> findAllByCardId(Long cardId);
}
