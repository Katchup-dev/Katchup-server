package site.katchup.katchupserver.api.card.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.dto.request.CardCreateRequestDto;
import site.katchup.katchupserver.api.card.dto.request.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.response.CardGetResponseDto;
import site.katchup.katchupserver.api.card.dto.response.CardListGetResponseDto;
import site.katchup.katchupserver.api.card.dto.response.FileGetResponseDto;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.keyword.domain.CardKeyword;
import site.katchup.katchupserver.api.keyword.dto.response.KeywordGetResponseDto;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.keyword.repository.KeywordRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetResponseDto;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.subTask.repository.SubTaskRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.trash.domain.Trash;
import site.katchup.katchupserver.api.trash.repository.TrashRepository;
import site.katchup.katchupserver.common.util.S3Util;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private final SubTaskRepository subTaskRepository;
    private final CardKeywordRepository cardKeywordRepository;
    private final TrashRepository trashRepository;
    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;
    private final ScreenshotRepository screenshotRepository;
    private final KeywordRepository keywordRepository;
    private final S3Util s3Util;

    @Override
    public List<CardListGetResponseDto> getCardList(Long taskId) {
        return subTaskRepository.findAllByTaskId(taskId).stream()
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
    public void createCard(CardCreateRequestDto requestDto) {

        SubTask subTask = subTaskRepository.findByIdOrThrow(requestDto.getSubTaskId());

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
            Screenshot findScreenshot = screenshotRepository.findByIdOrThrow(screenshotInfo.getScreenshotUUID());
            findScreenshot.updateScreenshotUrl(screenshotInfo.getScreenshotUrl());
            findScreenshot.updateCard(savedCard);
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

        return CardGetResponseDto.of(card.getId(), category.getName(), task.getName(), card.getSubTask().getName(), keywordResponseDtoList, screenshotResponseDtoList, fileGetResponseDTOList);
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
                        .of(screenshot.getId(), screenshot.getStickerOrder(), screenshot.getUrl())
                ).collect(Collectors.toList());
    }

    private List<FileGetResponseDto> getFileDtoList(Long cardId) {
        return cardRepository.findByIdOrThrow(cardId).getFiles().stream()
                .map(file -> FileGetResponseDto.of(file.getId(), file.getName(), file.getUrl(), file.getSize()))
                .collect(Collectors.toList());
    }
}
