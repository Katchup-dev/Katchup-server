package site.katchup.katchupserver.api.folder.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.dto.response.FolderResponseDto;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.folder.service.FolderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {

    private final FolderRepository folderRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<FolderResponseDto> getAllFolder(Long memberId) {
        return categoryRepository.findByMemberId(memberId).stream()
                .flatMap(category -> folderRepository.findByCategoryId(category.getId()).stream())
                .map(folder -> FolderResponseDto.of(folder.getId(), folder.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FolderResponseDto> getByCategoryId(Long categoryId) {
        return folderRepository.findByCategoryId(categoryId).stream()
                .map(folder -> FolderResponseDto.of(folder.getId(), folder.getName()))
                .collect(Collectors.toList());
    }
}
