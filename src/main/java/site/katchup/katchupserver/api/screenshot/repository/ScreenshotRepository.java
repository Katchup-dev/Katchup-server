package site.katchup.katchupserver.api.screenshot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.UUID;

public interface ScreenshotRepository extends JpaRepository<Screenshot, UUID> {
    List<Screenshot> findAllByCardId(Long cardId);
    void deleteById(UUID uuid);

    default Screenshot findByIdOrThrow(UUID uuid) {
        return findById(uuid).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_SCREENSHOT));
    }
}
