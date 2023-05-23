package site.katchup.katchupserver.api.folder.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.response.FolderResponseDto;
import site.katchup.katchupserver.api.folder.service.FolderService;
import site.katchup.katchupserver.api.member.domain.Member;
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

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<FolderResponseDto>> getAllCategory(Principal principal) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(SuccessStatus.READ_ALL_FOLDER_SUCCESS, folderService.getAllFolder(memberId));
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<FolderResponseDto>> getByCategoryId(@PathVariable final Long categoryId) {
        return ApiResponseDto.success(SuccessStatus.READ_BY_CATEGORY_SUCCESS, folderService.getByCategoryId(categoryId));
    }

    @PatchMapping("/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto updateFolderName(Principal principal, @PathVariable final Long folderId,
                                             @RequestBody final FolderUpdateRequestDto requestDto) {
        Long memberId = MemberUtil.getMemberId(principal);
        folderService.updateFolderName(folderId, requestDto);
        return ApiResponseDto.success(SuccessStatus.UPDATE_FOLDER_NAME_SUCCESS);
    }
}
