package site.katchup.katchupserver.api.screenshot.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.sticker.dto.response.StickerGetResponseDto;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
public class ScreenshotGetResponseDto {
    @Schema(description = "스크린샷 고유 id", example = "ddwd-wdwd-wdwd-wdwdwdwd")
    private UUID screenshotUUID;

    @Schema(description = "스크린샷 이름", example = "screenshot-name")
    private String screenshotName;

    @Schema(description = "스크린샷 url", example = "https://abde.s3.ap-northeast-2.amazonaws.com/1.png")
    private String screenshotUrl;

    @Schema(description = "스크린샷 업로드 일자", example = "2023/08/23")
    private String screenshotUploadDate;

    @Schema(description = "번호 스티커")
    private List<StickerGetResponseDto> stickerList;
}
