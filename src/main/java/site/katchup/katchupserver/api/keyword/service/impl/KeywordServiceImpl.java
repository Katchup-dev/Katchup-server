package site.katchup.katchupserver.api.keyword.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.TaskKeywordRepository;
import site.katchup.katchupserver.api.keyword.service.KeywordService;
import site.katchup.katchupserver.api.task.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final CategoryRepository categoryRepository;
    private final FolderRepository folderRepository;
    private final TaskRepository taskRepository;
    private final CardRepository cardRepository;
    private final TaskKeywordRepository taskKeywordRepository;
    private final KeywordRepository keywordRepository;
    @Override
    public List<KeywordResponseDto> getAllKeyword(Long memberId) {

        return categoryRepository.findByMemberId(memberId).stream()
                .flatMap(category -> folderRepository.findByCategoryId(category.getId()).stream())
                .flatMap(folder -> taskRepository.findByFolderId(folder.getId()).stream())
                .flatMap(task -> cardRepository.findByTaskId(task.getId()).stream())
                .flatMap(card -> taskKeywordRepository.findByCardId(card.getId()).stream())
                .flatMap(taskKeyword -> keywordRepository.findById(taskKeyword.getId()).stream())
                .map(keyword -> KeywordResponseDto.of(keyword.getId(), keyword.getName()))
                .collect(Collectors.toList());
    }
}
