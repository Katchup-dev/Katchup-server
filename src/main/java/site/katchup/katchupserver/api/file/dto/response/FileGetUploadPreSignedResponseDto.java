package site.katchup.katchupserver.api.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of")
@Schema(description = "파일 업로드 PreSigned-Url 응답 DTO")
public class FileGetUploadPreSignedResponseDto {

    @Schema(description = "파일 UUID", example = "file-uuid")
    private String fileUUID;

    @Schema(description = "파일 이름", example = "file-name")
    private String fileName;

    @Schema(description = "파일 업로드 PreSigned-Url", example ="preSigned-url")
    private String filePreSignedUrl;

    @Schema(description = "파일 업로드 일자", example = "2023/08/23")
    private String fileUploadDate;
}
