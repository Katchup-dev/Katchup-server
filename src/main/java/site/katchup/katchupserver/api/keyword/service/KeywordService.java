package site.katchup.katchupserver.api.keyword.service;

import site.katchup.katchupserver.api.keyword.dto.request.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;

import java.util.List;

public interface KeywordService {

    List<KeywordGetResponseDto> getAllKeyword(Long cardId);
    void createKeyword(KeywordCreateRequestDto requestDto);

}
