package site.katchup.katchupserver.api.category.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class CategoryResponseDto {

    private Long categoryId;
    private String name;
    private boolean isShared;

    public static CategoryResponseDto of(Long categoryId, String name, boolean isShared) {
        return new CategoryResponseDto(categoryId, name, isShared);
    }
}
