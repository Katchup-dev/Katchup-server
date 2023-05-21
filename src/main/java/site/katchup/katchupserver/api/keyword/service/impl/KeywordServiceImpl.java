package site.katchup.katchupserver.api.keyword.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.api.keyword.domain.TaskKeyword;
import site.katchup.katchupserver.api.keyword.dto.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.TaskKeywordRepository;
import site.katchup.katchupserver.api.keyword.service.KeywordService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final TaskKeywordRepository taskKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final CardRepository cardRepository;

    @Override
    public List<KeywordResponseDto> getAllKeyword(Long cardId) {

        return taskKeywordRepository.findByCardId(cardId).stream()
                .flatMap(taskKeyword -> keywordRepository.findById(taskKeyword.getKeyword().getId()).stream())
                .map(keyword -> KeywordResponseDto.of(keyword.getId(), keyword.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void createKeyword(Long cardId, KeywordCreateRequestDto requestDto) {

        Keyword keyword = Keyword.builder()
                        .name(requestDto.getName())
                        .build();

        keywordRepository.save(keyword);

        TaskKeyword taskKeyword = TaskKeyword.builder()
                .card(getCardById(cardId))
                .keyword(keyword)
                .build();

        taskKeywordRepository.save(taskKeyword);
    }

    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("해당 카드를 찾을 수 없습니다."));
    }
}
