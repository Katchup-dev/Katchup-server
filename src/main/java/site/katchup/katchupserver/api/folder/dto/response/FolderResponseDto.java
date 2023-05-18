package site.katchup.katchupserver.api.folder.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class FolderResponseDto {

    private Long folderId;
    private String name;

    public static FolderResponseDto of(Long folderId, String name) {
        return new FolderResponseDto(folderId, name);
    }
}
