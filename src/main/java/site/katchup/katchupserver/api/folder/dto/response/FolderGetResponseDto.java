package site.katchup.katchupserver.api.folder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class FolderGetResponseDto {

    private Long folderId;
    private String name;

    public static FolderGetResponseDto of(Long folderId, String name) {
        return new FolderGetResponseDto(folderId, name);
    }
}
