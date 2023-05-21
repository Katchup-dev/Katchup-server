package site.katchup.katchupserver.api.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.keyword.domain.Keyword;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Optional<Keyword> findById(Long taskKeywordId);
}
