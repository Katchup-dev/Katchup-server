package site.katchup.katchupserver.api.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.file.dto.request.FileGetPreSignedRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetPreSignedResponseDto;
import site.katchup.katchupserver.api.file.service.FileService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[File] 파일 관련 API (V1)")
public class FileController {
    private final FileService fileService;

    @Operation(summary = "파일 Presigned-Url 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "파일 Presigned-Url 요청 성공"),
            @ApiResponse(responseCode = "400", description = "파일 Presigned-Url 요청 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    @GetMapping("/files/presigned")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<FileGetPreSignedResponseDto> createPresigned(Principal principal, @RequestBody FileGetPreSignedRequestDto presignedRequestDto
    ) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(fileService.getFilePreSignedUrl(memberId, presignedRequestDto));
    }
}
