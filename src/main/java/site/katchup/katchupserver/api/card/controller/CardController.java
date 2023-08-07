package site.katchup.katchupserver.api.card.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.card.dto.request.CardCreateRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.response.CardGetResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

import java.util.List;

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
    public ApiResponseDto createCard(
            @RequestPart List<MultipartFile> fileList,
            CardCreateRequestDto cardCreateRequestDto ) {
        cardService.createCard(fileList, cardCreateRequestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_CARD_SUCCESS);
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
        return ApiResponseDto.success(SuccessStatus.GET_CARD_SUCCESS, cardService.getCard(cardId));
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
        return ApiResponseDto.success(SuccessStatus.DELETE_CARD_LIST_SUCCESS);
    }
}
