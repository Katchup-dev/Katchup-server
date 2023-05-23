package site.katchup.katchupserver.api.screenshot.service;

import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDto;

public interface ScreenshotService {

    UploadScreenshotResponseDto uploadScreenshot(MultipartFile file, Long cardId);

}
