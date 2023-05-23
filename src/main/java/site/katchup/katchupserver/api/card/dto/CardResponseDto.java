package site.katchup.katchupserver.api.card.dto;

import lombok.*;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.task.domain.Task;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class CardResponseDto {
    private Long taskId;
    private Long cardId;
    private Long placementOrder;
    private String cardName;
    private List<KeywordResponseDto> keywordList = new ArrayList<>();
    private String content;
    private Boolean existFile;

    public static CardResponseDto of (Card card, Task task, List<KeywordResponseDto> keywordList) {
        Boolean existFile = card.getFiles().isEmpty() ? false : true;
        return CardResponseDto.builder()
                .cardId(card.getId())
                .taskId(task.getId())
                .placementOrder(card.getPlacementOrder())
                .cardName(task.getName())
                .keywordList(keywordList)
                .content(card.getContent())
                .existFile(existFile)
                .build();
    }
}
