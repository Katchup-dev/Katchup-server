package site.katchup.katchupserver.api.screenshot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {

}
