package site.katchup.katchupserver.api.folder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.folder.dto.request.FolderCreateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.response.FolderResponseDto;
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
@Tag(name = "[Folder] 중분류 관련 API (V1)")
public class FolderController {

    private final FolderService folderService;
    private final CardService cardService;

    @Operation(summary = "중분류 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중분류 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "중분류 목록 조회 성공", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<FolderResponseDto>> getAllFolder(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.READ_ALL_FOLDER_SUCCESS, folderService.getAllFolder(memberId));
    }

    @Operation(summary = "중분류 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중분류 조회 성공"),
            @ApiResponse(responseCode = "400", description = "중분류 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<FolderResponseDto>> getByCategoryId(@PathVariable final Long categoryId) {
        return ApiResponseDto.success(SuccessStatus.READ_BY_CATEGORY_SUCCESS, folderService.getByCategoryId(categoryId));
    }

    @Operation(summary = "중분류 업데이트 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중분류 업데이트 성공"),
            @ApiResponse(responseCode = "400", description = "중분류 업데이트 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PatchMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateFolderName(@PathVariable final Long folderId,
                                             @RequestBody @Valid final FolderUpdateRequestDto requestDto) {
        folderService.updateFolderName(folderId, requestDto);
        return ApiResponseDto.success(SuccessStatus.UPDATE_FOLDER_NAME_SUCCESS);
    }

    @Operation(summary = "중분류 생성 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "중분류 생성 성공"),
            @ApiResponse(responseCode = "400", description = "중분류 생성 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto createFolderName(@RequestBody @Valid final FolderCreateRequestDto requestDto) {
        folderService.createFolderName(requestDto);
        return ApiResponseDto.success(SuccessStatus.CREATE_FOLDER_NAME_SUCCESS);
    }

    @Operation(summary = "중분류 목록 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중분류 목록 조회 성공"),
            @ApiResponse(responseCode = "400", description = "중분류 목록 조회 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/{folderId}/cards")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CardResponseDto>> getCardList(@PathVariable final Long folderId) {
        return ApiResponseDto.success(SuccessStatus.GET_ALL_CARD_SUCCESS, cardService.getCardList(folderId));
    }

    @Operation(summary = "중분류 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "중분류 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "중분류 삭제 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ApiResponseDto.success(SuccessStatus.DELETE_FOLDER_SUCCESS);
    }
}
