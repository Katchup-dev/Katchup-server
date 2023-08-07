package site.katchup.katchupserver.api.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class CardCreateRequestDto {
    @NotBlank(message = "대분류는 필수 값입니다.")
    private Long categoryId;
    @NotBlank(message = "중분류는 필수 값입니다.")
    private Long folderId;
    @NotBlank(message = "소분류는 필수 값입니다.")
    private Long taskId;
    @NotBlank(message = "키워드는 필수 값입니다.")
    private List<Long> keywordIdList;
    private String note;
    @NotBlank(message = "내용은 필수 값입니다.")
    private String content;

}
