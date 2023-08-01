package site.katchup.katchupserver.api.keyword.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "[Keyword] 키워드 관련 API (V1)")
public class KeywordController {
    private final KeywordService keywordService;

    @Operation(summary = "업무 카드의 모든 키워드 조회 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "카드의 모든 키워드 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "카드의 모든 키워드 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    @GetMapping("/{cardId}/keywords")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<KeywordResponseDto>> getAllKeyword(@PathVariable Long cardId) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_KEYWORD_SUCCESS, keywordService.getAllKeyword(cardId));
    }

    @Operation(summary = "키워드 생성 API")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "키워드 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "키워드 생성 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    @PostMapping("/{cardId}/keywords")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createKeyword(@PathVariable Long cardId, @Valid @RequestBody KeywordCreateRequestDto requestDto) {
        keywordService.createKeyword(cardId, requestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_KEYWORD_SUCCESS);
    }
}
