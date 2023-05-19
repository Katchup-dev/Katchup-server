package site.katchup.katchupserver.api.screenshot.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ScreenshotValidator {
    private static final Set<String> VALID_CONTENT_TYPES = new HashSet<>(Arrays.asList("image/png", "image/jpg", "image/jpeg", "image/gif","image/tiff", "image/tif"));

    // 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public void validate(MultipartFile file) throws IllegalArgumentException{
            if (file.isEmpty()) {
                throw new IllegalArgumentException("빈 파일입니다.");
            }

            if (!VALID_CONTENT_TYPES.contains(file.getContentType())) {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
            }

            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("5MB 미만의 파일을 업로드해야합니다.");
        }
    }

}
