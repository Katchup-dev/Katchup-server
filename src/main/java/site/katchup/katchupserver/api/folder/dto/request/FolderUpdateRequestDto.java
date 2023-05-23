package site.katchup.katchupserver.api.folder.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class FolderUpdateRequestDto {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣_\\s]*$", message = "이모지 및 특수기호 입력은 불가능합니다. 제외하여 입력해 주세요.")
    private String name;
}
