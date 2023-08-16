package site.katchup.katchupserver.api.keyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    List<Keyword> findAllByTaskId(Long taskId);
    default Keyword findByIdOrThrow(Long keywordId) {
        return findById(keywordId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_KEYWORD));
    };
}
