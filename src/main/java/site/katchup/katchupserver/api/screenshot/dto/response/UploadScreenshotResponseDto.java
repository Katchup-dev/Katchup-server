package site.katchup.katchupserver.api.screenshot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UploadScreenshotResponseDto {

    private String id;
    private String screenshotUrl;

    @Builder
    public UploadScreenshotResponseDto(String id, String screenshotUrl) {
        this.id = id;
        this.screenshotUrl = screenshotUrl;
    }
}
