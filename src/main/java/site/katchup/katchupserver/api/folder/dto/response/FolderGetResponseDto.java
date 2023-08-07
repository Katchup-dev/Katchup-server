package site.katchup.katchupserver.api.folder.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Schema(description = "폴더 응답 DTO")
public class FolderGetResponseDto {
    @Schema(description = "폴더 고유 id", example = "1")
    private Long folderId;
    @Schema(description = "폴더 이름", example = "Katchup Design")
    private String name;

    public static FolderGetResponseDto of(Long folderId, String name) {
        return new FolderGetResponseDto(folderId, name);
    }
}
