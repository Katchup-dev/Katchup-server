package site.katchup.katchupserver.api.screenshot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

import static site.katchup.katchupserver.common.dto.ApiResponseDto.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[Screenshot] 스크린샷 관련 API (V1)")
public class ScreenshotController {

    private final ScreenshotService screenshotService;

    @Operation(summary = "스크린샷 Presigned-Url 요청 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "스크린샷 Presigned-Url 요청 성공"),
        @ApiResponse(responseCode = "400", description = "스크린샷 Presigned-Url 요청 실패", content = @Content),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    @GetMapping("/screenshots/presigned")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<ScreenshotGetPreSignedResponseDto> createPresigned(Principal principal, @RequestParam String screenshotName
    ) {
        Long memberId = MemberUtil.getMemberId(principal);
        return ApiResponseDto.success(screenshotService.getUploadPreSignedUrl(memberId, screenshotName));
    }

    @Operation(summary = "스크린샷 삭제 API")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "스크린샷 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "스크린샷 삭제 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    @DeleteMapping("/screenshots")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto delete(Principal principal, @RequestParam(value = "screenshotName") String screenshotName
            , @RequestParam(value = "screenshotUploadDate") String screenshotUploadDate, @RequestParam(value = "screenshotUUID") String screenshotUUID) {
        Long memberId = MemberUtil.getMemberId(principal);
        screenshotService.deleteFile(memberId, screenshotName, screenshotUploadDate, screenshotUUID);
        return success();
    }

}
