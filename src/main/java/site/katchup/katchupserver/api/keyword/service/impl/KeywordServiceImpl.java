package site.katchup.katchupserver.api.keyword.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.api.keyword.domain.CardKeyword;
import site.katchup.katchupserver.api.keyword.dto.request.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.keyword.service.KeywordService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final CardKeywordRepository cardKeywordRepository;
    private final KeywordRepository keywordRepository;
    private final CardRepository cardRepository;

    @Override
    public List<KeywordGetResponseDto> getAllKeyword(Long cardId) {

        return cardKeywordRepository.findByCardId(cardId).stream()
                .flatMap(taskKeyword -> keywordRepository.findById(taskKeyword.getKeyword().getId()).stream())
                .map(KeywordGetResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    public void createKeyword(Long cardId, KeywordCreateRequestDto requestDto) {

        Keyword keyword = Keyword.builder()
                        .name(requestDto.getName())
                        .build();

        keywordRepository.save(keyword);

        CardKeyword taskKeyword = CardKeyword.builder()
                .card(cardRepository.findByIdOrThrow(cardId))
                .keyword(keyword)
                .build();

        cardKeywordRepository.save(taskKeyword);
    }
}
