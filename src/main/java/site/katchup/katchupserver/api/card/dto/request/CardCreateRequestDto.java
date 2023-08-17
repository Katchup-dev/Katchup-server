package site.katchup.katchupserver.api.card.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
public class CardCreateRequestDto {
    @NotBlank(message = "CD-110")
    private Long categoryId;
    @NotBlank(message = "CD-111")
    private Long taskId;
    @NotBlank(message = "CD-112")
    private Long subTaskId;
    @NotBlank(message = "CD-113")
    private List<Long> keywordIdList;
    private String note;
    @NotBlank(message = "CD-114")
    private String content;

}
