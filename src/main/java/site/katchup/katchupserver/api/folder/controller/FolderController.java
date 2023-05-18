package site.katchup.katchupserver.api.folder.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import site.katchup.katchupserver.api.folder.dto.response.FolderResponseDto;
import site.katchup.katchupserver.api.folder.service.FolderService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

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
        Long memberId = Member.getMemberId(principal);

        return ApiResponseDto.success(SuccessStatus.READ_ALL_FOLDER_SUCCESS, folderService.getAllFolder(memberId));
    }
}
