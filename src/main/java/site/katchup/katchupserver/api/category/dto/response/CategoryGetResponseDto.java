package site.katchup.katchupserver.api.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class CategoryGetResponseDto {

    private Long categoryId;
    private String name;
    private Boolean isShared;

    public static CategoryGetResponseDto of(Long categoryId, String name, boolean isShared) {
        return new CategoryGetResponseDto(categoryId, name, isShared);
    }
}
