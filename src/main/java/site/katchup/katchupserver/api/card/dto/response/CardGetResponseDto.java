package site.katchup.katchupserver.api.card.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetResponseDto;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class CardGetResponseDto {
    private Long cardId;
    private String category;
    private String folder;
    private String task;
    private List<KeywordGetResponseDto> keywordList;
    private List<ScreenshotGetResponseDto> screenshotList;
    private List<FileGetResponseDto> fileList;
}
