package site.katchup.katchupserver.api.card.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.sticker.dto.request.StickerCreateRequestDto;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class CardCreateRequestDto {
    @NotNull(message = "CD-110")
    private Long categoryId;

    @NotNull(message = "CD-111")
    private Long taskId;

    @Schema(description = "세부 업무 작성 안할 시, 해당 값 0으로")
    @NotNull(message = "CD-112")
    private Long subTaskId;

    @NotNull(message = "CD-113")
    private List<Long> keywordIdList;

    private List<ScreenshotCreateRequestDto> screenshotList;

    private String note;

    @NotBlank(message = "CD-114")
    private String content;
}
