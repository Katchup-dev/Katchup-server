package site.katchup.katchupserver.api.card.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.card.dto.request.CardCreateRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardUpdateRequestDto;
import site.katchup.katchupserver.api.card.dto.response.CardGetResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
@Tag(name = "[Card] 업무 카드 관련 API (V1)")
public class CardController {
    private final CardService cardService;

    @Operation(summary = "업무 카드 생성 API")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201", description = "업무 카드 생성 성공"),
                    @ApiResponse(responseCode = "400", description = "업무 카드 생성 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createCard(Principal principal,
            @Valid @RequestBody CardCreateRequestDto cardCreateRequestDto ) {
        Long memberId = MemberUtil.getMemberId(principal);
        cardService.createCard(memberId, cardCreateRequestDto);
        return ApiResponseDto.success();
    }

    @Operation(summary = "업무 카드 조회 API")
    @GetMapping("/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "업무 카드 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "업무 카드 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "404", description = "업무 카드 조회 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    public ApiResponseDto<CardGetResponseDto> getCard(@PathVariable Long cardId) {
        return ApiResponseDto.success(cardService.getCard(cardId));
    }

    @Operation(summary = "업무 카드 삭제 API")
    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "업무 카드 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "업무 카드 삭제 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    public ApiResponseDto deleteCards(@RequestBody final CardDeleteRequestDto cardDeleteRequestDto) {
        cardService.deleteCardList(cardDeleteRequestDto);
        return ApiResponseDto.success();
    }

    @Operation(summary = "업무 카드 수정 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "업무 카드 수정 성공"),
            @ApiResponse(responseCode = "400", description = "업무 카드 수정 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PatchMapping("/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateCard(Principal principal, @PathVariable Long cardId,
                                     @Valid @RequestBody CardUpdateRequestDto cardUpdateRequestDto ) {
        Long memberId = MemberUtil.getMemberId(principal);
        cardService.updateCard(memberId, cardId, cardUpdateRequestDto);
        return ApiResponseDto.success();
    }
}
