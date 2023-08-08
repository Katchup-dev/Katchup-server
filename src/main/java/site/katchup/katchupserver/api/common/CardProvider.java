package site.katchup.katchupserver.api.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorStatus;

@Service
@RequiredArgsConstructor
public class CardProvider {

    private final CardRepository cardRepository;

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_CARD));
    }
}
