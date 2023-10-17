package site.katchup.katchupserver.api.category.service;

import site.katchup.katchupserver.api.category.dto.request.CategoryCreateRequestDto;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryGetResponseDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryPatchSharedStatusResponseDto;

import java.util.List;

public interface CategoryService {

    Long createCategoryName(Long memberId, CategoryCreateRequestDto categoryCreateRequestDto);
    List<CategoryGetResponseDto> getAllCategory(Long memberId);
    void updateCategoryName(Long memberId, Long categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto);
    void deleteCategory(Long categoryId);
    CategoryPatchSharedStatusResponseDto toggleSharedStatus(Long categoryId);

}