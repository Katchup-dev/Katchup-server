package site.katchup.katchupserver.api.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor(staticName = "of")
@Schema(description = "파일 다운로드 PreSigned-Url 응답 DTO")
public class FileGetDownloadPreSignedResponseDto {

    @Schema(description = "파일 다운로드 PreSigned-Url", example ="preSigned-url")
    private String filePreSignedUrl;

}
