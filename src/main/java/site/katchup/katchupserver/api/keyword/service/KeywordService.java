package site.katchup.katchupserver.api.keyword.service;

import site.katchup.katchupserver.api.keyword.dto.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;

import java.util.List;

public interface KeywordService {
    List<KeywordResponseDto> getAllKeyword(Long cardId);
    void createKeyword(Long cardId, KeywordCreateRequestDto requestDto);

}
