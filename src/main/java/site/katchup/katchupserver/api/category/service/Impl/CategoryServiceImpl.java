package site.katchup.katchupserver.api.category.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.api.common.CardProvider;
import site.katchup.katchupserver.common.response.ErrorStatus;

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

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category findCategory = getById(categoryId);

        categoryRepository.deleteById(categoryId);

        findCategory.getFolders().stream()
                .flatMap(folder -> folder.getTasks().stream())
                .forEach(task -> task.getCards()
                        .forEach(Card::deletedCard));
    }

    private Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_CATEGORY.getMessage()));
    }
}