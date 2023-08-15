package site.katchup.katchupserver.api.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByCategoryId(Long categoryId);

    boolean existsByCategoryIdAndName(Long categoryId, String name);

    default Folder findByIdOrThrow(Long folderId) {
        return findById(folderId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_FOLDER));
    }
}