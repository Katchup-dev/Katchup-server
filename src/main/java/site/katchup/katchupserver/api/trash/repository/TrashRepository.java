package site.katchup.katchupserver.api.trash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.trash.domain.Trash;

@Repository
public interface TrashRepository extends JpaRepository<Trash, Long> {
}
