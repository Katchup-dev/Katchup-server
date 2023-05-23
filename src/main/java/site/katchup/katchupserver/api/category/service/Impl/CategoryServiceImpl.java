package site.katchup.katchupserver.api.category.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

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
    public void updateCategoryName(Long categoryId, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Category findCategory = categoryRepository.getById(categoryId);
        findCategory.updateCategoryName(categoryUpdateRequestDto.getName());
    }
}
