package site.katchup.katchupserver.api.screenshot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Schema(description = "스크린샷 업로드 응답 DTO")
public class ScreenshotUploadResponseDto {
    @Schema(description = "스크린샷 고유 id", example = "1")
    private String id;
    @Schema(description = "스크린샷 url", example ="https://~")
    private String screenshotUrl;

    @Builder
    public ScreenshotUploadResponseDto(String id, String screenshotUrl) {
        this.id = id;
        this.screenshotUrl = screenshotUrl;
    }
}
