package site.katchup.katchupserver.api.category.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
public class CategoryPatchSharedStatusResponseDto {
    private Boolean isShared;

    public static CategoryPatchSharedStatusResponseDto of(boolean isShared) {
        return new CategoryPatchSharedStatusResponseDto(isShared);
    }
}
