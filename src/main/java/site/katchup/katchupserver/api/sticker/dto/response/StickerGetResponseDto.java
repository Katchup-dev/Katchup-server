package site.katchup.katchupserver.api.sticker.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of")
@Schema(description = "번호 스티커 응답 DTO")
public class StickerGetResponseDto {
    @Schema(description = "스티커 번호", example = "2")
    private Integer order;

    @Schema(description = "스티커 X좌표", example = "300")
    private Float x;

    @Schema(description = "스티커 Y좌표", example = "400")
    private Float y;
}
