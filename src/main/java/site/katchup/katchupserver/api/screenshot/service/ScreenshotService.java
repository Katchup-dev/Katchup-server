package site.katchup.katchupserver.api.screenshot.service;

import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDto;

import java.util.UUID;

public interface ScreenshotService {

    UploadScreenshotResponseDto uploadScreenshot(MultipartFile file, Long cardId);

    void delete(Long cardId, String screenshotId);
}
