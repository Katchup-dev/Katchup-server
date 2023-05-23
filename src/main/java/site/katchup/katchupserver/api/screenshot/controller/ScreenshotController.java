package site.katchup.katchupserver.api.screenshot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDto;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;

import java.security.Principal;

import static site.katchup.katchupserver.common.dto.ApiResponseDto.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScreenshotController {

    private final ScreenshotService screenshotService;

    @PostMapping("/cards/{cardId}/screenshot")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<UploadScreenshotResponseDto> uploadScreenshot(
            Principal principal,
            @PathVariable Long cardId,
            @RequestPart MultipartFile file
    ) {
        return success(SuccessStatus.UPLOAD_SCREENSHOT_SUCCESS, screenshotService.uploadScreenshot(file, cardId));
    }

    @DeleteMapping("/cards/{cardId}/screenshots/{screenshotId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto deleteScreenshot(
            Principal principal, @PathVariable Long cardId, @PathVariable String screenshotId
    ) {
        screenshotService.delete(cardId, screenshotId);
        return success(SuccessStatus.DELETE_SCREENSHOT_SUCCESS);
    }

}
