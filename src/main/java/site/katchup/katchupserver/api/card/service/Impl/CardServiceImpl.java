package site.katchup.katchupserver.api.card.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardGetResponseDto;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;
import site.katchup.katchupserver.api.card.dto.FileResponseDto;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.card.repository.FileRepository;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.common.CardProvider;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotResponseDto;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.trash.domain.Trash;
import site.katchup.katchupserver.api.trash.repository.TrashRepository;
import site.katchup.katchupserver.common.exception.EntityNotFoundException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final TaskRepository taskRepository;
    private final CardKeywordRepository cardKeywordRepository;
    private final TrashRepository trashRepository;
    private final CardProvider cardProvider;
    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final ScreenshotRepository screenshotRepository;

    @Override
    @Transactional
    public List<CardResponseDto> getCardList(Long folderId) {
        return taskRepository.findByFolderId(folderId).stream()
                .flatMap(task -> task.getCards().stream())
                .collect(Collectors.groupingBy(Card::getTask))  // taskId로 그룹화
                .values().stream()
                .flatMap(cards -> cards.stream().sorted(Comparator.comparing(Card::getPlacementOrder)))  // 그룹 내에서 placementOrder 값으로 정렬
                .map(card -> {
                    List<KeywordResponseDto> keywords = cardKeywordRepository.findByCardId(card.getId()).stream()
                            .map(cardKeyword -> KeywordResponseDto.of(cardKeyword.getKeyword()))
                            .collect(Collectors.toList());
                    Task relatedTask = card.getTask();  // Card와 연관된 Task 가져오기
                    return CardResponseDto.of(card, relatedTask, keywords);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCardList(CardDeleteRequestDto cardDeleteRequestDto) {
        cardDeleteRequestDto.getCardIdList().stream()
                .map(cardProvider::getCardById)
                .forEach(findCard -> {
                    findCard.deletedCard();
                    trashRepository.save(Trash.builder().card(findCard).build());
        });
    }

    @Override
    public CardGetResponseDto getCard(Long cardId) {
        Card card = getCardById(cardId);
        Folder folder = getFolderById(card.getTask().getFolder().getId());
        Category category = getCategoryById(folder.getCategory().getId());

        List<KeywordResponseDto> keywordResponseDtoList = getKeywordDtoList(cardId);
        List<FileResponseDto> fileResponseDTOList = getFileDtoList(cardId);
        List<ScreenshotResponseDto> screenshotResponseDtoList = getScreenshotDtoList(cardId);

        return CardGetResponseDto.of(
                card.getId(),
                category.getName(),
                folder.getName(),
                card.getTask().getName(),
                keywordResponseDtoList,
                screenshotResponseDtoList,
                fileResponseDTOList
        );
    }

    private List<KeywordResponseDto> getKeywordDtoList(Long cardId) {
        return cardKeywordRepository.findByCardId(cardId)
                .stream()
                .map(cardKeyword -> KeywordResponseDto.of(cardKeyword.getKeyword().getId(), cardKeyword.getKeyword().getName()))
                .collect(Collectors.toList());
    }
    private List<ScreenshotResponseDto> getScreenshotDtoList(Long cardId) {
        return screenshotRepository.findAllByCardId(cardId).stream()
                .map(screenshot -> ScreenshotResponseDto
                        .of(screenshot.getId(), screenshot.getStickerOrder(), screenshot.getUrl())
                ).collect(Collectors.toList());
    }
    private List<FileResponseDto> getFileDtoList(Long cardId) {
        return fileRepository.findAllByCardId(cardId).stream()
                .map(file -> FileResponseDto
                        .of(file.getId(), file.getName(), file.getUrl(), file.getSize())
                ).collect(Collectors.toList());
    }

    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_CARD)
        );
    }

    private Folder getFolderById(Long folderId) {
        return folderRepository.findById(folderId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_FOLDER));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_CATEGORY));
    }
}
