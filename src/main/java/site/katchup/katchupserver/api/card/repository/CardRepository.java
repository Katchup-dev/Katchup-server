package site.katchup.katchupserver.api.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByTaskId(Long taskId);

    default Card findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_CARD));
    }
}