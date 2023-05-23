package site.katchup.katchupserver.api.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.folder.domain.Folder;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByCategoryId(Long categoryId);
    boolean existsByCategoryIdAndName(Long categoryId, String name);
}