package site.katchup.katchupserver.api.card.service;

import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.dto.request.CardCreateRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardUpdateRequestDto;
import site.katchup.katchupserver.api.card.dto.response.CardGetResponseDto;
import site.katchup.katchupserver.api.card.dto.response.CardListGetResponseDto;

import java.util.List;

@Transactional
public interface CardService {

    List<CardListGetResponseDto> getCardList(Long taskId);
    CardGetResponseDto getCard(Long cardId);
    void deleteCardList(CardDeleteRequestDto cardDeleteRequestDto);
    Long createCard(Long memberId, CardCreateRequestDto cardCreateRequestDto);
    void updateCard(Long memberId, Long cardId, CardUpdateRequestDto cardUpdateRequestDto);

}
