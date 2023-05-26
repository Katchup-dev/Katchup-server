package site.katchup.katchupserver.api.card.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class FileResponseDto {
    private Long id;
    private String name;
    private String url;
    private Double size;
}
