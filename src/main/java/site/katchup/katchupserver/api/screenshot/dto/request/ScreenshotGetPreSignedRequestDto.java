package site.katchup.katchupserver.api.screenshot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Schema(description = "스크린샷 Presigned-Url 요청 DTO")
public class ScreenshotGetPreSignedRequestDto {
    @Schema(description = "업로드하는 스크린샷 이름", example = "capture.png")
    @NotNull
    private String screenshotName;
}
