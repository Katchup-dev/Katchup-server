package site.katchup.katchupserver.api.folder.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.card.dto.response.CardListGetResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.folder.dto.request.FolderCreateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.response.FolderGetResponseDto;
import site.katchup.katchupserver.api.folder.service.FolderService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor(access = PRIVATE)
@RequestMapping("/api/v1/folders")
public class FolderController {

    private final FolderService folderService;
    private final CardService cardService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<FolderGetResponseDto>> getAllFolder(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.READ_ALL_FOLDER_SUCCESS, folderService.getAllFolder(memberId));
    }

    @GetMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<FolderGetResponseDto>> getByCategoryId(@PathVariable final Long categoryId) {
        return ApiResponseDto.success(SuccessStatus.READ_BY_CATEGORY_SUCCESS, folderService.getByCategoryId(categoryId));
    }

    @PatchMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateFolderName(@PathVariable final Long folderId,
                                             @RequestBody @Valid final FolderUpdateRequestDto requestDto) {
        folderService.updateFolderName(folderId, requestDto);
        return ApiResponseDto.success(SuccessStatus.UPDATE_FOLDER_NAME_SUCCESS);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createFolderName(@RequestBody @Valid final FolderCreateRequestDto requestDto) {
        folderService.createFolderName(requestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_FOLDER_NAME_SUCCESS);
    }

    @GetMapping("/{folderId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CardListGetResponseDto>> getCardList(@PathVariable final Long folderId) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_CARD_SUCCESS, cardService.getCardList(folderId));
    }
    @DeleteMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ApiResponseDto.success(SuccessStatus.DELETE_FOLDER_SUCCESS);
    }
}
