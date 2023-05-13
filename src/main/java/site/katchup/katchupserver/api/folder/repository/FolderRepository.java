package site.katchup.katchupserver.api.folder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.folder.domain.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
}