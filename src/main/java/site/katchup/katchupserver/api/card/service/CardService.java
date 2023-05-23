package site.katchup.katchupserver.api.card.service;

import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardGetResponseDto;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;

import java.util.List;

@Transactional
public interface CardService {

    List<CardResponseDto> getCardList(Long folderId);
    CardGetResponseDto getCard(Long cardId);

    void deleteCardList(CardDeleteRequestDto cardDeleteRequestDto);


}
