package site.katchup.katchupserver.api.card.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotResponseDto;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class CardGetResponseDto {
    private Long cardId;
    private String category;
    private String folder;
    private String task;
    private List<KeywordResponseDto> keywordList;
    private List<ScreenshotResponseDto> screenshotList;
    private List<FileResponseDto> fileList;
}
