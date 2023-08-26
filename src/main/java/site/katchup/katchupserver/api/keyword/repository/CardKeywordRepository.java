package site.katchup.katchupserver.api.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.keyword.domain.CardKeyword;

import java.util.List;

public interface CardKeywordRepository extends JpaRepository<CardKeyword, Long> {
    List<CardKeyword> findAllByCardId(Long cardId);
}
