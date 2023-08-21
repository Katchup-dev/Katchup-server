package site.katchup.katchupserver.api.screenshot.service;

import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotGetPreSignedRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;

public interface ScreenshotService {

    ScreenshotGetPreSignedResponseDto getScreenshotPreSignedUrl(Long memberId, ScreenshotGetPreSignedRequestDto requestDto);
    void deleteScreenshot(Long cardId, String screenshotId);

}
