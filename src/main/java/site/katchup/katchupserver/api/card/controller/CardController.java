package site.katchup.katchupserver.api.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public ApiResponseDto<CardGetResponseDto> getCard(@PathVariable Long cardId) {
        return ApiResponseDto.success(SuccessStatus.GET_CARD_SUCCESS, cardService.getCard(cardId));
    }
}
