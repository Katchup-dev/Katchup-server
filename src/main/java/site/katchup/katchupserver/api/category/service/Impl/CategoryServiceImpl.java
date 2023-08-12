package site.katchup.katchupserver.api.category.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.dto.request.CategoryCreateRequestDto;
import site.katchup.katchupserver.api.category.dto.request.CategoryUpdateRequestDto;
import site.katchup.katchupserver.api.category.dto.response.CategoryGetResponseDto;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.CategoryService;

import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.exception.BadRequestException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void createCategoryName(Long memberId, CategoryCreateRequestDto requestDto) {
        checkDuplicateCategoryName(memberId, requestDto.getName());

        categoryRepository.save(Category.builder()
                .name(requestDto.getName())
                .member(memberRepository.findByIdOrThrow(memberId))
                .isShared(false)
                .build());
    }

    @Override
    @Transactional
    public List<CategoryGetResponseDto> getAllCategory(Long memberId) {
        return categoryRepository.findByMemberId(memberId).stream()
                .map(category -> CategoryGetResponseDto.of(category.getId(), category.getName(), category.isShared()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateCategoryName(Long memberId, Long categoryId, CategoryUpdateRequestDto requestDto) {
        Category findCategory = categoryRepository.findByIdOrThrow(categoryId);

        checkDuplicateCategoryName(memberId, requestDto.getName());

        findCategory.updateCategoryName(requestDto.getName());
    }

    @Override
    @Transactional
    public void deleteCategory(Long categoryId) {
        Category findCategory = categoryRepository.findByIdOrThrow(categoryId);
        findCategory.deleted();
        findCategory.getFolders().forEach(this::deleteFolderAndTaskAndCard);
    }

    private void checkDuplicateCategoryName(Long memberId, String name) {
        if (categoryRepository.existsByMemberIdAndName(memberId, name))
            throw new BadRequestException(ErrorCode.DUPLICATE_FOLDER_NAME);
    }

    private void deleteFolderAndTaskAndCard(Folder folder) {
        folder.deleted();
        folder.getTasks().forEach(Task::deleted);
        folder.getTasks().stream()
                .flatMap(task -> task.getCards().stream())
                .forEach(Card::deletedCard);
    }
}