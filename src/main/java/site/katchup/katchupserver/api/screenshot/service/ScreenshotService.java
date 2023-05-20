package site.katchup.katchupserver.api.screenshot.service;

import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDTO;

public interface ScreenshotService {

    UploadScreenshotResponseDTO uploadScreenshot(MultipartFile file, Long cardId);

}
