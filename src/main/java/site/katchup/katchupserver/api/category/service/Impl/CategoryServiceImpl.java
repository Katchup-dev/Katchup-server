package site.katchup.katchupserver.api.category.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.request.CategoryCreateRequestDto;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;

import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.exception.CustomException;
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
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_CATEGORY.getMessage()));

        if (checkDuplicateCategoryName(memberId, requestDto.getName())) {
            throw new CustomException(ErrorStatus.DUPLICATE_FOLDER_NAME);
        }

        findCategory.updateCategoryName(requestDto.getName());
    }

    private boolean checkDuplicateCategoryName(Long memberId, String name) {
        return categoryRepository.existsByMemberIdAndName(memberId, name);
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category findCategory = getById(categoryId);
        findCategory.deleted();
        findCategory.getFolders().forEach(this::deleteFolderAndTaskAndCard);
    }

    private Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_CATEGORY.getMessage()));
    }

    private void deleteFolderAndTaskAndCard(Folder folder) {
        folder.deleted();
        folder.getTasks().forEach(Task::deleted);
        folder.getTasks().stream()
                .flatMap(task -> task.getCards().stream())
                .forEach(Card::deletedCard);
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.INVALID_MEMBER));
    }
}