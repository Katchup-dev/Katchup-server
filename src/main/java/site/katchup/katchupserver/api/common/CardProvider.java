package site.katchup.katchupserver.api.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorCode;

@Service
@RequiredArgsConstructor
public class CardProvider { //repository로 빼기

    private final CardRepository cardRepository;

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_CARD));
    }
}
