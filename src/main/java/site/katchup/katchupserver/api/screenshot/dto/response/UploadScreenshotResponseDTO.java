package site.katchup.katchupserver.api.screenshot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UploadScreenshotResponseDTO {

    private String id;
    private String screenshotUrl;

    @Builder
    public UploadScreenshotResponseDTO(String id, String screenshotUrl) {
        this.id = id;
        this.screenshotUrl = screenshotUrl;
    }
}
