package site.katchup.katchupserver.api.screenshot.service;

import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotGetPreSignedRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;

import java.util.UUID;

public interface ScreenshotService {

    ScreenshotGetPreSignedResponseDto getPreSignedUrl(Long memberId, ScreenshotGetPreSignedRequestDto requestDto);
    void delete(Long cardId, String screenshotId);
    String findUrl(Long memberId, ScreenshotCreateRequestDto requestDto);

}
