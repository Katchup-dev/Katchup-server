package site.katchup.katchupserver.api.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.file.dto.response.FileGetDownloadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetUploadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.service.FileService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

import static site.katchup.katchupserver.common.dto.ApiResponseDto.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[File] 파일 관련 API (V1)")
public class FileController {
    private final FileService fileService;

    @Operation(summary = "파일 업로드 Presigned-Url 요청 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드 Presigned-Url 요청 성공"),
            @ApiResponse(responseCode = "400", description = "파일 업로드 Presigned-Url 요청 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    }
    )
    @GetMapping("/files/presigned/upload")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<FileGetUploadPreSignedResponseDto> upload(Principal principal, @RequestParam String fileName
    ) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(fileService.getUploadPreSignedUrl(memberId, fileName));
    }

    @Operation(summary = "파일 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "파일 삭제 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @DeleteMapping("/files")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto delete(Principal principal, @RequestParam(value = "fileOriginalName") String fileOriginalName
            , @RequestParam(value = "fileUploadDate") String fileUploadDate, @RequestParam(value = "fileUUID") String fileUUID) {
        Long memberId = MemberUtil.getMemberId(principal);
        fileService.deleteFile(memberId, fileOriginalName, fileUploadDate, fileUUID);
        return success();
    }

    @Operation(summary = "파일 다운로드 PreSigned-Url API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 다운로드 Presigned-Url 요청 성공"),
            @ApiResponse(responseCode = "400", description = "파일 다운로드 Presigned-Url 요청 실패", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @GetMapping("/files/presigned/download")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<FileGetDownloadPreSignedResponseDto> download(@RequestParam(value = "fileUUID") String fileUUID, @RequestParam(value = "fileName") String fileName) {
        return success(fileService.getDownloadPreSignedUrl(fileUUID, fileName));
    }
}
