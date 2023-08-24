package site.katchup.katchupserver.api.keyword.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.api.keyword.dto.request.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.keyword.service.KeywordService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final KeywordRepository keywordRepository;

    @Override
    @Transactional(readOnly = true)
    public List<KeywordGetResponseDto> getAllKeyword(Long taskId) {
        return keywordRepository.findAllByTaskId(taskId).stream()
                .map(KeywordGetResponseDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long createKeyword(KeywordCreateRequestDto requestDto) {

        Keyword keyword = Keyword.builder()
                        .name(requestDto.getName())
                        .color(requestDto.getColor())
                        .taskId(requestDto.getTaskId())
                        .build();

        Keyword saveKeyword = keywordRepository.save(keyword);
        return saveKeyword.getId();
    }
}
