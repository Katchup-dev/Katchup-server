package site.katchup.katchupserver.api.screenshot.service;

import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;

public interface ScreenshotService {

    ScreenshotGetPreSignedResponseDto getPreSignedUrl(Long memberId, String screenshotName);

    @Transactional
    String createKey(Long memberId, ScreenshotCreateRequestDto requestDto);

    void delete(String screenshotId);
    String findUrl(Long memberId, ScreenshotCreateRequestDto requestDto);

}
