package site.katchup.katchupserver.api.card.service;

import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;

import java.util.List;

public interface CardService {
    //* 대분류 > 중분류 > 업무 카드 조회
    List<CardResponseDto> getCardList(Long folderId);

    void deleteCardList(CardDeleteRequestDto cardIdList);

}
