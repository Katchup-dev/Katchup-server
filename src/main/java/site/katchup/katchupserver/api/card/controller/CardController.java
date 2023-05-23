package site.katchup.katchupserver.api.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardGetResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cards")
public class CardController {
    private final CardService cardService;

    @GetMapping("/{cardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CardGetResponseDto> getCard(@PathVariable Long cardId) {
        return ApiResponseDto.success(SuccessStatus.GET_CARD_SUCCESS, cardService.getCard(cardId));
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteCards(@RequestBody final CardDeleteRequestDto cardDeleteRequestDto) {
        cardService.deleteCardList(cardDeleteRequestDto);
        return ApiResponseDto.success(SuccessStatus.DELETE_CARD_LIST_SUCCESS);
    }
}
