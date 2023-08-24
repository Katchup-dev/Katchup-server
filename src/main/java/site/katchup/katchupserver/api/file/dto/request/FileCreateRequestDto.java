package site.katchup.katchupserver.api.file.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Schema(description = "파일 업로드 요청 DTO")
public class FileCreateRequestDto {

    @Schema(description = "업로드하는 파일 고유 UUID", example = "ddwd-wdwd-wdwd-wdwdwdwd")
    @NotNull
    private UUID fileUUID;

    @Schema(description = "파일 이름", example = "와이어프레임 및 드라이브 사용법.pdf")
    @NotNull
    private String fileName;

    @Schema(description = "파일 업로드 일자", example = "2023/08/23")
    @NotNull
    private String fileUploadDate;

    @Schema(description = "파일 사이즈 (KB 단위로 저장)", example = "189277")
    @NotNull
    private int size;

}
