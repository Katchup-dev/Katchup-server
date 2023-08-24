package site.katchup.katchupserver.api.screenshot.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.sticker.dto.request.StickerCreateRequestDto;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Schema(description = "스크린샷 업로드 요청 DTO")
public class ScreenshotCreateRequestDto {
    @Schema(description = "업로드하는 스크린샷 고유 UUID", example = "ddwd-wdwd-wdwd-wdwdwdwd")
    @NotNull
    private UUID screenshotUUID;

    @Schema(description = "스크린샷 이름", example = "스크린샷 이름.jpg")
    @NotNull
    private String screenshotName;

    @Schema(description = "스크린샷 업로드 일자", example = "2023/08/23")
    @NotNull
    private String screenshotUploadDate;

    @Schema(description = "번호 스티커")
    private List<StickerCreateRequestDto> stickerList;
}
