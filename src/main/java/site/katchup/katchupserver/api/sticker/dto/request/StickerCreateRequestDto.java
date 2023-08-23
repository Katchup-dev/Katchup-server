package site.katchup.katchupserver.api.sticker.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "번호 스티커 추가 요청 DTO")
public class StickerCreateRequestDto {
    @Schema(description = "스티커 번호", example = "2")
    private Integer order;

    @Schema(description = "스티커 X좌표", example = "300")
    private Float x;

    @Schema(description = "스티커 Y좌표", example = "400")
    private Float y;
}
