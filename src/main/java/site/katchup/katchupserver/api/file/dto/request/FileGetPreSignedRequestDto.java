package site.katchup.katchupserver.api.file.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
@Schema(description = "파일 Presigned-Url 요청 DTO")
public class FileGetPreSignedRequestDto {
    @Schema(description = "업로드하는 파일 이름", example = "와이어프레임 및 드라이브 사용법.pdf")
    @NotNull
    private String fileName;
}
