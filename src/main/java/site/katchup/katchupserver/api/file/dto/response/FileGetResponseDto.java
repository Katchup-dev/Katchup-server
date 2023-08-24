package site.katchup.katchupserver.api.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(staticName = "of")
public class FileGetResponseDto {
    @Schema(description = "파일 고유 id", example = "ddwd-wdwd-wdwd-wdwdwdwd")
    private UUID id;

    @Schema(description = "파일 이름", example = "와이어프레임 및 드라이브 사용법.pdf")
    private String name;

    @Schema(description = "파일 url", example = "https://abde.s3.ap-northeast-2.amazonaws.com/1.png")
    private String url;

    @Schema(description = "파일 사이즈 (KB 단위로 저장)", example = "189277")
    private int size;
}
