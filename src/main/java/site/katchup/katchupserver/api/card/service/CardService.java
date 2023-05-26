package site.katchup.katchupserver.api.card.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.card.dto.CardCreateRequestDto;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardGetResponseDto;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;

import java.util.List;

@Transactional
public interface CardService {

    List<CardResponseDto> getCardList(Long folderId);
    CardGetResponseDto getCard(Long cardId);

    void deleteCardList(CardDeleteRequestDto cardDeleteRequestDto);

    void createCard(List<MultipartFile> fileList, CardCreateRequestDto cardCreateRequestDto);


}
