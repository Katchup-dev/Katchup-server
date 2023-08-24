package site.katchup.katchupserver.api.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.file.domain.File;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<File, UUID> {

    List<File> findAllByCardId(Long cardId);

    default File findByIdOrThrow(UUID uuid) {
        return findById(uuid).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_FILE));
    }
}
