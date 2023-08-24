package site.katchup.katchupserver.api.screenshot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "스크린샷 PreSigned-Url 응답 DTO")
public class ScreenshotGetPreSignedResponseDto {

    @Schema(description = "스크린샷 UUID", example = "screenshot-uuid")
    private String screenshotUUID;

    @Schema(description = "스크린샷 PreSigned-Url", example ="preSigned-url")
    private String screenshotPreSignedUrl;

    @Schema(description = "스크린샷 업로드 일자", example = "2023/08/23")
    private String screenshotUploadDate;

    public static ScreenshotGetPreSignedResponseDto of (String screenshotUUID, String screenshotPreSignedUrl, String screenshotUploadDate) {
        return new ScreenshotGetPreSignedResponseDto(screenshotUUID, screenshotPreSignedUrl, screenshotUploadDate);
    }
}
