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
import site.katchup.katchupserver.common.exception.NotFoundException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.util.List;
import java.util.stream.Collectors;

import static site.katchup.katchupserver.common.response.ErrorStatus.NOT_FOUND_CATEGORY;
import static site.katchup.katchupserver.common.response.ErrorStatus.NOT_FOUND_FOLDER;

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
        Folder findFolder = folderRepository.findById(folderId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_FOLDER));

        if (checkDuplicateFolderName(findFolder.getCategory().getId(), requestDto.getName())) {
            throw new BadRequestException(ErrorStatus.DUPLICATE_FOLDER_NAME);
        }

        findFolder.updateFolderName(requestDto.getName());
    }

    @Override
    @Transactional
    public void createFolderName(FolderCreateRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_CATEGORY));
        if (checkDuplicateFolderName(requestDto.getCategoryId(), requestDto.getName())) {
            throw new BadRequestException(ErrorStatus.DUPLICATE_FOLDER_NAME);
        }

        folderRepository.save(Folder.builder()
                .name(requestDto.getName())
                .category(category)
                .isDeleted(false)
                .build());
    }

    private boolean checkDuplicateFolderName(Long categoryId, String name) {
        return folderRepository.existsByCategoryIdAndName(categoryId, name);
    }

    public void deleteFolder(Long folderId) {
        Folder findFolder = getFolderById(folderId);

        findFolder.deleted();
        findFolder.getTasks().forEach(this::deleteTaskAndCard);
    }

    private void deleteTaskAndCard(Task task) {
        task.deleted();
        task.getCards().stream().forEach(Card::deletedCard);
    }

    private Folder getFolderById(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_FOLDER));
    }
}
