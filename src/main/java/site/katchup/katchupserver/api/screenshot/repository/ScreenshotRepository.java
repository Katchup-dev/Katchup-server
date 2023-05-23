package site.katchup.katchupserver.api.screenshot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;

import java.util.UUID;

public interface ScreenshotRepository extends JpaRepository<Screenshot, UUID> {

}
