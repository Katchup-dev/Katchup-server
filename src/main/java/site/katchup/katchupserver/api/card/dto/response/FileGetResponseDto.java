package site.katchup.katchupserver.api.card.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class FileGetResponseDto {
    private Long id;
    private String name;
    private String url;
    private Double size;
}
