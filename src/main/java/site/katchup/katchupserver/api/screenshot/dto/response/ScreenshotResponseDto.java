package site.katchup.katchupserver.api.screenshot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
public class ScreenshotResponseDto {
    private UUID id;
    private int stickerOrder;
    private String url;
}
