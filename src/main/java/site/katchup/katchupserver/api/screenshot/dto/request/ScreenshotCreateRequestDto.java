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

    @Schema(description = "스크린샷 url", example = "https://abde.s3.ap-northeast-2.amazonaws.com/1.png")
    @NotNull
    private String screenshotUrl;

    @Schema(description = "번호 스티커")
    private List<StickerCreateRequestDto> stickerList;
}
