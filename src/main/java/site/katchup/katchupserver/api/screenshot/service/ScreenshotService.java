package site.katchup.katchupserver.api.screenshot.service;

import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;

public interface ScreenshotService {

    ScreenshotGetPreSignedResponseDto getPreSignedUrl(Long memberId, String screenshotName);
    void delete(Long cardId, String screenshotId);
    String findUrl(Long memberId, ScreenshotCreateRequestDto requestDto);

}
