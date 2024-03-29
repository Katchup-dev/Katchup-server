package site.katchup.katchupserver.api.screenshot.service;

import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;

public interface ScreenshotService {

    ScreenshotGetPreSignedResponseDto getUploadPreSignedUrl(Long memberId, String screenshotName);

    String createKey(Long memberId, String screenshotDate, String screenshotUUID, String screenshotName);

    void deleteFile(Long memberId, String screenshotName, String screenshotUploadDate, String screenshotUUID);

    String findUrl(Long memberId, ScreenshotCreateRequestDto requestDto);

}
