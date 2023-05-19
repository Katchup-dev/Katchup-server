package site.katchup.katchupserver.api.screenshot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDTO;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.common.dto.ApiResponseDto;
import site.katchup.katchupserver.common.response.SuccessStatus;
import site.katchup.katchupserver.common.util.MemberUtil;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ScreenshotController {

    private final ScreenshotService screenshotService;

    @PostMapping("/cards/{cardId}/screenshot")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<UploadScreenshotResponseDTO> uploadScreenshot(
            Principal principal,
            @PathVariable("cardId") Long cardId,
            @RequestPart MultipartFile file
    ) {
        return ApiResponseDto.success(SuccessStatus.UPLOAD_SCREENSHOT_SUCCESS, screenshotService.uploadScreenshot(file, cardId));
    }

}
