package site.katchup.katchupserver.api.card.dto.response;

import lombok.*;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;
import site.katchup.katchupserver.api.subTask.domain.SubTask;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class CardListGetResponseDto {
    private Long subTaskId;
    private Long cardId;
    private Long placementOrder;
    private String subTaskName;
    private List<KeywordGetResponseDto> keywordList = new ArrayList<>();
    private String content;
    private Boolean existFile;
    private Boolean existScreenshot;

    public static CardListGetResponseDto of (Card card, SubTask subTask, List<KeywordGetResponseDto> keywordList) {
        Boolean existFile = card.getFiles().isEmpty() ? false : true;
        Boolean existScreenshot = card.getScreenshots().isEmpty() ? false : true;
        return CardListGetResponseDto.builder()
                .cardId(card.getId())
                .subTaskId(subTask.getId())
                .placementOrder(card.getPlacementOrder())
                .subTaskName(subTask.getName())
                .keywordList(keywordList)
                .content(card.getContent())
                .existFile(existFile)
                .existScreenshot(existScreenshot)
                .build();
    }
}
