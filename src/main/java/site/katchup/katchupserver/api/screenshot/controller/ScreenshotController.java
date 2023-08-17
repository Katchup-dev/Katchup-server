package site.katchup.katchupserver.api.screenshot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotUploadResponseDto;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;

import java.security.Principal;

import static site.katchup.katchupserver.common.dto.ApiResponseDto.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "[Screenshot] 스크린샷 관련 API (V1)")
public class ScreenshotController {

    private final ScreenshotService screenshotService;

    @Operation(summary = "스크린샷 업로드 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "스크린샷 업로드 성공"),
        @ApiResponse(responseCode = "400", description = "스크린샷 업로드 실패", content = @Content),
        @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            }
    )
    @PostMapping("/cards/{cardId}/screenshot")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<ScreenshotUploadResponseDto> uploadScreenshot(
            Principal principal,
            @PathVariable Long cardId,
            @RequestPart MultipartFile file
    ) {
        return success(screenshotService.uploadScreenshot(file, cardId));
    }

    @Operation(summary = "스크린샷 삭제 API")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "스크린샷 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "스크린샷 삭제 실패", content = @Content),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
            })
    @DeleteMapping("/cards/{cardId}/screenshots/{screenshotId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteScreenshot(
            Principal principal, @PathVariable Long cardId, @PathVariable String screenshotId
    ) {
        screenshotService.deleteScreenshot(cardId, screenshotId);
        return success();
    }

}
