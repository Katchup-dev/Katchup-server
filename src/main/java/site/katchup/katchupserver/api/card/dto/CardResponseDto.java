package site.katchup.katchupserver.api.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
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

    public static CardResponseDto of (Card card, Task task, List<KeywordResponseDto> keywordList) {
        return CardResponseDto.builder()
                .cardId(card.getId())
                .taskId(task.getId())
                .placementOrder(card.getPlacementOrder())
                .cardName(task.getName())
                .keywordList(keywordList)
                .content(card.getContent())
                .build();
    }
}
