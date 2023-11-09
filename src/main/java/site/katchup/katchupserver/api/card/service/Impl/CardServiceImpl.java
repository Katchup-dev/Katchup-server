package site.katchup.katchupserver.api.card.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.dto.request.CardCreateRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardUpdateRequestDto;
import site.katchup.katchupserver.api.card.dto.response.CardGetResponseDto;
import site.katchup.katchupserver.api.card.dto.response.CardListGetResponseDto;
import site.katchup.katchupserver.api.file.domain.File;
import site.katchup.katchupserver.api.file.dto.request.FileCreateRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetResponseDto;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.file.repository.FileRepository;
import site.katchup.katchupserver.api.file.service.FileService;
import site.katchup.katchupserver.api.keyword.domain.CardKeyword;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetResponseDto;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.api.sticker.domain.Sticker;
import site.katchup.katchupserver.api.sticker.dto.request.StickerCreateRequestDto;
import site.katchup.katchupserver.api.sticker.dto.response.StickerGetResponseDto;
import site.katchup.katchupserver.api.sticker.repository.StickerRepository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.subTask.repository.SubTaskRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.trash.domain.Trash;
import site.katchup.katchupserver.api.trash.repository.TrashRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private static final String SUB_TASK_ETC_NAME = "기타";
    private static final Long SUB_TASK_ETC_ID = 0L;

    private final SubTaskRepository subTaskRepository;
    private final CardKeywordRepository cardKeywordRepository;
    private final TrashRepository trashRepository;
    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final ScreenshotRepository screenshotRepository;
    private final KeywordRepository keywordRepository;
    private final StickerRepository stickerRepository;
    private final FileRepository fileRepository;
    private final ScreenshotService screenshotService;
    private final FileService fileService;

    @Override
    public List<CardListGetResponseDto> getCardList(Long taskId) {
        return subTaskRepository.findAllByTaskIdAndNotDeleted(taskId).stream()
                .flatMap(subTask -> subTask.getCards().stream())
                .collect(Collectors.groupingBy(Card::getSubTask))  // subTaskId 그룹화
                .values().stream()
                .flatMap(cards -> cards.stream().sorted(Comparator.comparing(Card::getPlacementOrder)))  // 그룹 내에서 placementOrder 값으로 정렬
                .filter(card -> !card.isDeleted())  // isDeleted가 false인 card 필터링
                .map(card -> {
                    List<KeywordGetResponseDto> keywords = cardKeywordRepository.findAllByCardId(card.getId()).stream()
                            .map(cardKeyword -> KeywordGetResponseDto.of(cardKeyword.getKeyword()))
                            .collect(Collectors.toList());
                    SubTask relatedSubTask = card.getSubTask();  // Card와 연관된 SubTask 가져오기
                    return CardListGetResponseDto.of(card, relatedSubTask, keywords);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCardList(CardDeleteRequestDto cardDeleteRequestDto) {
        cardDeleteRequestDto.getCardIdList().stream()
                .map(cardRepository::findByIdOrThrow)
                .forEach(findCard -> {
                    findCard.deletedCard();
                    trashRepository.save(Trash.builder().card(findCard).build());
        });
    }

    @Override
    @Transactional
    public Long createCard(Long memberId, CardCreateRequestDto requestDto) {

        SubTask subTask;
        if (requestDto.getSubTaskId() == SUB_TASK_ETC_ID) {
            Task task = taskRepository.findByIdOrThrow(requestDto.getTaskId());
            subTask = subTaskRepository.findOrCreateEtcSubTask(task, SUB_TASK_ETC_NAME);
        } else {
            subTask = subTaskRepository.findByIdOrThrow(requestDto.getSubTaskId());
        }

        Card card = Card.builder()
                .content(requestDto.getContent())
                .note(requestDto.getNote())
                .placementOrder(getPlacementOrder(subTask))
                .subTask(subTask)
                .build();

        Card savedCard = cardRepository.save(card);

        for (Long keywordId : requestDto.getKeywordIdList()) {
            CardKeyword cardKeyword = CardKeyword.builder()
                    .card(savedCard)
                    .keyword(keywordRepository.findByIdOrThrow(keywordId))
                    .build();
            cardKeywordRepository.save(cardKeyword);
        }

        for (ScreenshotCreateRequestDto screenshotInfo : requestDto.getScreenshotList()) {
            Screenshot newScreenshot = Screenshot.builder()
                    .id(screenshotInfo.getScreenshotUUID())
                    .screenshotName(screenshotInfo.getScreenshotName())
                    .screenshotKey(screenshotService.createKey(memberId, screenshotInfo.getScreenshotUploadDate(), screenshotInfo.getScreenshotUUID().toString(),
                            screenshotInfo.getScreenshotName()))
                    .url(screenshotService.findUrl(memberId, screenshotInfo))
                    .uploadDate(screenshotInfo.getScreenshotUploadDate())
                    .card(savedCard)
                    .build();

            screenshotRepository.save(newScreenshot);

            for (StickerCreateRequestDto stickerInfo : screenshotInfo.getStickerList()) {
                Sticker newSticker = Sticker.builder()
                        .order(stickerInfo.getOrder())
                        .x(stickerInfo.getX())
                        .y(stickerInfo.getY())
                        .screenshot(newScreenshot)
                        .build();
                stickerRepository.save(newSticker);
            }
        }

        for (FileCreateRequestDto fileInfo : requestDto.getFileList()) {
            File newFile = File.builder()
                    .id(fileInfo.getFileUUID())
                    .fileKey(fileService.createKey(memberId, fileInfo.getFileUploadDate(), fileInfo.getFileUUID().toString()
                    , fileInfo.getFileOriginalName()))
                    .originalName(fileInfo.getFileOriginalName())
                    .changedName(fileInfo.getFileChangedName())
                    .uploadDate(fileInfo.getFileUploadDate())
                    .size(fileInfo.getSize())
                    .card(savedCard)
                    .build();

            fileRepository.save(newFile);
        }

        return savedCard.getId();
    }

    @Override
    @Transactional
    public void updateCard(Long memberId, Long cardId, CardUpdateRequestDto requestDto) {

        SubTask subTask;
        if (requestDto.getSubTaskId() == SUB_TASK_ETC_ID) {
            Task task = taskRepository.findByIdOrThrow(requestDto.getTaskId());
            subTask = subTaskRepository.findOrCreateEtcSubTask(task, SUB_TASK_ETC_NAME);
        } else {
            subTask = subTaskRepository.findByIdOrThrow(requestDto.getSubTaskId());
        }

        Card card = cardRepository.findByIdOrThrow(cardId);
        card.updateCard(getPlacementOrder(subTask), requestDto.getContent(), requestDto.getNote(), subTask);

        List<CardKeyword> cardKeywordList = cardKeywordRepository.findAllByCardId(cardId);
        cardKeywordRepository.deleteAll(cardKeywordList);

        for (Long keywordId : requestDto.getKeywordIdList()) {
            CardKeyword cardKeyword = CardKeyword.builder()
                    .card(card)
                    .keyword(keywordRepository.findByIdOrThrow(keywordId))
                    .build();
            cardKeywordRepository.save(cardKeyword);
        }

        for (ScreenshotCreateRequestDto screenshotInfo : requestDto.getScreenshotList()) {
            Screenshot newScreenshot = Screenshot.builder()
                    .id(screenshotInfo.getScreenshotUUID())
                    .screenshotKey(screenshotService.createKey(memberId, screenshotInfo.getScreenshotUploadDate(), screenshotInfo.getScreenshotUUID().toString(),
                            screenshotInfo.getScreenshotName()))
                    .url(screenshotService.findUrl(memberId, screenshotInfo))
                    .uploadDate(screenshotInfo.getScreenshotUploadDate())
                    .screenshotName(screenshotInfo.getScreenshotName())
                    .card(card)
                    .build();

            screenshotRepository.save(newScreenshot);
        }

        for (FileCreateRequestDto fileInfo : requestDto.getFileList()) {
            File newFile = File.builder()
                    .id(fileInfo.getFileUUID())
                    .fileKey(fileService.createKey(memberId, fileInfo.getFileUploadDate(), fileInfo.getFileUUID().toString()
                    , fileInfo.getFileOriginalName()))
                    .originalName(fileInfo.getFileOriginalName())
                    .changedName(fileInfo.getFileChangedName())
                    .uploadDate(fileInfo.getFileUploadDate())
                    .size(fileInfo.getSize())
                    .card(card)
                    .build();

            fileRepository.save(newFile);
        }
    }

    @Override
    public CardGetResponseDto getCard(Long cardId) {
        Card card = cardRepository.findByIdOrThrow(cardId);
        Task task = taskRepository.findByIdOrThrow(card.getSubTask().getTask().getId());
        Category category = categoryRepository.findByIdOrThrow(task.getCategory().getId());

        List<KeywordGetResponseDto> keywordResponseDtoList = getKeywordDtoList(cardId);
        List<FileGetResponseDto> fileGetResponseDTOList = getFileDtoList(cardId);
        List<ScreenshotGetResponseDto> screenshotResponseDtoList = getScreenshotDtoList(cardId);

        return CardGetResponseDto.of(card.getId(), category.getId(), category.getName(),
                task.getId(), task.getName(), card.getSubTask().getId(), card.getSubTask().getName(), card.getContent(), card.getNote(), keywordResponseDtoList, screenshotResponseDtoList, fileGetResponseDTOList);
    }

    private Long getPlacementOrder(SubTask subTask) {
        if (subTask.getCards().size() == 0) {
            Task task = taskRepository.findByIdOrThrow(subTask.getTask().getId());
            List<SubTask> subTaskList = task.getSubTasks().stream().sorted(Comparator.comparing(SubTask::getName)).collect(Collectors.toList());
            Long placementOrder = 0L;

            for (SubTask sortedSubTask: subTaskList) {
                placementOrder += sortedSubTask.getCards().size();
                if (sortedSubTask.getId().equals(subTask.getId())) {
                    placementOrder += 1;
                    break;
                }
            }
            return placementOrder;
            // subTask 위치에서 1번째 카드 생성
        } else {
            // subTask가 갖고 있는 카드 중에서 마지막 카드의 바로 아래 생성
            Task task = taskRepository.findByIdOrThrow(subTask.getTask().getId());
            List<Card> cardList = subTask.getCards().stream().collect(Collectors.toList());
            Card maxPlacmentOrderCard = cardList.stream().max(Comparator.comparing(Card::getPlacementOrder)).get();
            Long maxPlacementOrder = maxPlacmentOrderCard.getPlacementOrder();
            Long placementOrder = maxPlacementOrder + 1;
            return placementOrder;
        }
    }

    private List<KeywordGetResponseDto> getKeywordDtoList(Long cardId) {
        return cardKeywordRepository.findAllByCardId(cardId).stream()
                .map(cardKeyword -> KeywordGetResponseDto.of(cardKeyword.getKeyword().getId(),
                        cardKeyword.getKeyword().getName(), cardKeyword.getKeyword().getColor()))
                .collect(Collectors.toList());
    }
    private List<ScreenshotGetResponseDto> getScreenshotDtoList(Long cardId) {
        return cardRepository.findByIdOrThrow(cardId).getScreenshots().stream()
                .map(screenshot -> ScreenshotGetResponseDto
                        .of(screenshot.getId(), screenshot.getScreenshotName(), screenshot.getUrl(), screenshot.getUploadDate(), getStickerDtoList(screenshot.getId())))
                                .collect(Collectors.toList());
    }

    private List<FileGetResponseDto> getFileDtoList(Long cardId) {
        return cardRepository.findByIdOrThrow(cardId).getFiles().stream()
                .map(file -> FileGetResponseDto.of(file.getId(), file.getOriginalName(), file.getChangedName(), file.getUploadDate(), file.getSize()))
                .collect(Collectors.toList());
    }

    private List<StickerGetResponseDto> getStickerDtoList(UUID screenshotId) {
        return screenshotRepository.findByIdOrThrow(screenshotId).getSticker().stream()
                .map(sticker -> StickerGetResponseDto
                        .of(sticker.getOrder(), sticker.getX(), sticker.getY())
                ).collect(Collectors.toList());
    }
}
