package site.katchup.katchupserver.api.keyword.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    @Override
    public List<KeywordResponseDto> getAllKeyword(Long cardId) {

        return taskKeywordRepository.findByCardId(cardId).stream()
                .flatMap(taskKeyword -> keywordRepository.findById(taskKeyword.getKeyword().getId()).stream())
                .map(keyword -> KeywordResponseDto.of(keyword.getId(), keyword.getName()))
                .collect(Collectors.toList());
    }
}
