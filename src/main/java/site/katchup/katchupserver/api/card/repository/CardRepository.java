package site.katchup.katchupserver.api.card.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByTaskId(Long taskId);

    default Card getCardByIdOrThrow(Long cardId) {
        Card card = findById(cardId).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_FOUND_CARD)
        );

        if (card.isDeleted()) {
            throw new NotFoundException(ErrorCode.DELETED_CARD);
        }
        return card;
    }
}