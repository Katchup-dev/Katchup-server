package site.katchup.katchupserver.api.keyword.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.common.CardProvider;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.api.keyword.domain.CardKeyword;
import site.katchup.katchupserver.api.keyword.dto.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.keyword.service.KeywordService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final CardKeywordRepository taskKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final CardProvider cardProvider;

    @Override
    public List<KeywordResponseDto> getAllKeyword(Long cardId) {

        return taskKeywordRepository.findByCardId(cardId).stream()
                .flatMap(taskKeyword -> keywordRepository.findById(taskKeyword.getKeyword().getId()).stream())
                .map(keyword -> KeywordResponseDto.of(keyword))
                .collect(Collectors.toList());
    }

    @Override
    public void createKeyword(Long cardId, KeywordCreateRequestDto requestDto) {

        Keyword keyword = Keyword.builder()
                        .name(requestDto.getName())
                        .build();

        keywordRepository.save(keyword);

        CardKeyword taskKeyword = CardKeyword.builder()
                .card(cardProvider.getCardById(cardId))
                .keyword(keyword)
                .build();

        taskKeywordRepository.save(taskKeyword);
    }
}
