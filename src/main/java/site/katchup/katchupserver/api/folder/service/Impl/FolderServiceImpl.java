package site.katchup.katchupserver.api.folder.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.dto.request.FolderCreateRequestDto;
import site.katchup.katchupserver.api.folder.dto.request.FolderUpdateRequestDto;
import site.katchup.katchupserver.api.folder.dto.response.FolderGetResponseDto;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.folder.service.FolderService;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.common.exception.BadRequestException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public List<FolderGetResponseDto> getAllFolder(Long memberId) {
        return categoryRepository.findByMemberId(memberId).stream()
                .flatMap(category -> folderRepository.findByCategoryId(category.getId()).stream())
                .map(folder -> FolderGetResponseDto.of(folder.getId(), folder.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<FolderGetResponseDto> getByCategoryId(Long categoryId) {
        return folderRepository.findByCategoryId(categoryId).stream()
                .map(folder -> FolderGetResponseDto.of(folder.getId(), folder.getName()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateFolderName(Long folderId, FolderUpdateRequestDto requestDto) {
        Folder findFolder = folderRepository.findByIdOrThrow(folderId);

        checkDuplicateFolderName(findFolder.getCategory().getId(), requestDto.getName());

        findFolder.updateFolderName(requestDto.getName());
    }

    @Override
    @Transactional
    public void createFolderName(FolderCreateRequestDto requestDto) {
        Category findCategory = categoryRepository.findByIdOrThrow(requestDto.getCategoryId());

        checkDuplicateFolderName(findCategory.getId(), requestDto.getName());

        folderRepository.save(Folder.builder()
                .name(requestDto.getName())
                .category(findCategory)
                .isDeleted(false)
                .build());
    }

    private void checkDuplicateFolderName(Long categoryId, String name) {
        if (folderRepository.existsByCategoryIdAndName(categoryId, name))
            throw new BadRequestException(ErrorCode.DUPLICATE_FOLDER_NAME);
    }

    public void deleteFolder(Long folderId) {
        Folder findFolder = folderRepository.findByIdOrThrow(folderId);

        findFolder.deleted();
        findFolder.getTasks().forEach(this::deleteTaskAndCard);
    }

    private void deleteTaskAndCard(Task task) {
        task.deleted();
        task.getCards().stream().forEach(Card::deletedCard);
    }
}
