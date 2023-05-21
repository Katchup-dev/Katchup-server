package site.katchup.katchupserver.api.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.keyword.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
