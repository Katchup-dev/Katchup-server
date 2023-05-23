package site.katchup.katchupserver.api.card.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

import java.security.Principal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CardResponseDto>> getCardList(@PathVariable final Long folderId) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_CARD_SUCCESS, cardService.getCardList(folderId));
    }

    @PatchMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteCardList(@RequestBody final CardDeleteRequestDto cardDeleteRequestDto) {
        cardService.deleteCardList(cardDeleteRequestDto);
        return ApiResponseDto.success(SuccessStatus.DELETE_CARD_LIST_SUCCESS);
    }
}
