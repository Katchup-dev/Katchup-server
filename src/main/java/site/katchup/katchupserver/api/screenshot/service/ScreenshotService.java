package site.katchup.katchupserver.api.screenshot.service;

import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDTO;

import java.util.UUID;

public interface ScreenshotService {

    UploadScreenshotResponseDTO uploadScreenshot(MultipartFile file, Long cardId);

    void delete(Long cardId, String screenshotId);
}
