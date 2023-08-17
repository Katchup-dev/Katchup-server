package site.katchup.katchupserver.api.task.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class TaskUpdateRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9가-힣_\s]*$", message = "KC-103")
    @NotBlank(message = "TK-109")
    private String name;
}
