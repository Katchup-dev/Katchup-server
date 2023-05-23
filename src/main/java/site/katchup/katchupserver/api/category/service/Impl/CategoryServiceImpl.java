package site.katchup.katchupserver.api.category.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.request.CategoryCreateRequestDto;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.exception.EntityNotFoundException;
import site.katchup.katchupserver.api.common.CardProvider;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.util.List;
import java.util.stream.Collectors;

import static site.katchup.katchupserver.common.response.ErrorStatus.NOT_FOUND_CATEGORY;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

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
            throw new CustomException(ErrorStatus.DUPLICATE_CATEGORY_NAME);
        }

        findCategory.updateCategoryName(requestDto.getName());
    }

    @Override
    @Transactional
    public void createCategoryName(Long memberId, CategoryCreateRequestDto requestDto) {
        if (checkDuplicateCategoryName(memberId, requestDto.getName())) {
            throw new CustomException(ErrorStatus.DUPLICATE_CATEGORY_NAME);
        }

        categoryRepository.save(Category.builder()
                .name(requestDto.getName())
                .member(findMember(memberId))
                .isShared(false)
                .build());
    }

    private boolean checkDuplicateCategoryName(Long memberId, String name) {
        return categoryRepository.existsByMemberIdAndName(memberId, name);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.INVALID_MEMBER));
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
