package site.katchup.katchupserver.api.keyword.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.keyword.dto.KeywordCreateRequestDto;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.service.KeywordService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/{cardId}/keywords")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<KeywordResponseDto>> getAllKeyword(@PathVariable Long cardId) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_KEYWORD_SUCCESS, keywordService.getAllKeyword(cardId));
    }

    @PostMapping("/{cardId}/keywords")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createKeyword(@PathVariable Long cardId, @Valid @RequestBody KeywordCreateRequestDto requestDto) {
        keywordService.createKeyword(cardId, requestDto);

        return ApiResponseDto.success(SuccessStatus.CREATE_KEYWORD_SUCCESS);
    }
}
