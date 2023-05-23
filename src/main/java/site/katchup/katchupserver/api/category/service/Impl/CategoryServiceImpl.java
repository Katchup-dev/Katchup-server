package site.katchup.katchupserver.api.category.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.exception.EntityNotFoundException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.util.List;
import java.util.stream.Collectors;

import static site.katchup.katchupserver.common.response.ErrorStatus.NOT_FOUND_CATEGORY;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<CategoryResponseDto> getAllCategory(Long memberId) {
        return categoryRepository.findByMemberId(memberId).stream()
                .map(category -> CategoryResponseDto.of(category.getId(), category.getName(), category.isShared()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateCategoryName(Long memberId, Long categoryId, CategoryUpdateRequestDto requestDto) {
        Category findCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CATEGORY));

        if (checkDuplicateCategoryName(memberId, requestDto.getName())) {
            throw new CustomException(ErrorStatus.DUPLICATE_FOLDER_NAME);
        }

        findCategory.updateCategoryName(requestDto.getName());
    }

    private boolean checkDuplicateCategoryName(Long memberId, String name) {
        return categoryRepository.existsByMemberIdAndName(memberId, name);
    }
}
