package site.katchup.katchupserver.api.card.service.Impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.domain.File;
import site.katchup.katchupserver.api.card.dto.*;
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
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.exception.EntityNotFoundException;
import site.katchup.katchupserver.common.response.ErrorStatus;
import site.katchup.katchupserver.common.util.S3Util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private final S3Util s3Util;

    private static final String FILE_FOLDER_NAME = "files/";
    private static final String PDF_TYPE = "application/pdf";
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final long MB = 1024 * 1024;

    @Override
    @Transactional
    public List<CardResponseDto> getCardList(Long folderId) {
        return taskRepository.findByFolderId(folderId).stream()
                .flatMap(task -> task.getCards().stream())
                .collect(Collectors.groupingBy(Card::getTask))  // taskId로 그룹화
                .values().stream()
                .flatMap(cards -> cards.stream().sorted(Comparator.comparing(Card::getPlacementOrder)))  // 그룹 내에서 placementOrder 값으로 정렬
                .filter(card -> !card.isDeleted())  // isDeleted가 false인 card 필터링
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
    @Transactional
    public void createCard(List<MultipartFile> fileList, CardCreateRequestDto requestDto) {

        Task task = getTaskById(requestDto.getTaskId());

        Card card = Card.builder()
                .content(requestDto.getContent())
                .note(requestDto.getNote())
                .placementOrder(getPlacementOrder(task))
                .task(task)
                .build();

        cardRepository.save(card);


        try {
            for (MultipartFile file : fileList) {
                String fileName = UUID.randomUUID().toString() + ".pdf";
                validatePdf(file);
                validateFileSize(file);
                String uploadPath = FILE_FOLDER_NAME + getFoldername() + fileName;
                String fileUrl = s3Util.upload(getInputStream(file), uploadPath, getObjectMetadata(file));
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                fileRepository.save(File.builder()
                        .card(card)
                        .name(file.getOriginalFilename())
                        .url(fileUrl)
                        .size( Double.parseDouble(decimalFormat.format((double) file.getSize() / MB)))
                        .build());
            }
        } catch (IOException e) {
            throw new CustomException(ErrorStatus.FILE_UPLOAD_ERROR);
        }
    }

    @Override
    public CardGetResponseDto getCard(Long cardId) {
        Card card = getCardById(cardId);
        Folder folder = getFolderById(card.getTask().getFolder().getId());
        Category category = getCategoryById(folder.getCategory().getId());

        List<KeywordResponseDto> keywordResponseDtoList = getKeywordDtoList(cardId);
        List<FileResponseDto> fileResponseDTOList = getFileDtoList(cardId);
        List<ScreenshotResponseDto> screenshotResponseDtoList = getScreenshotDtoList(cardId);

        return CardGetResponseDto.of(card.getId(), category.getName(), folder.getName(), card.getTask().getName(), keywordResponseDtoList, screenshotResponseDtoList, fileResponseDTOList);
    }

    private Long getPlacementOrder(Task task) {
        if (task.getCards().size() == 0) {
            Folder folder = getFolderById(task.getFolder().getId());
            List<Task> taskList = folder.getTasks().stream().sorted(Comparator.comparing(Task::getName)).collect(Collectors.toList());
            Long placementOrder = 0L;

            for (Task sortedTask: taskList) {
                placementOrder += sortedTask.getCards().size();
                if (sortedTask.getId().equals(task.getId())) {
                    placementOrder += 1;
                    break;
                }
            }
            return placementOrder;
            // task 위치에서 1번째 카드 생성
        } else {
            // task가 갖고 있는 카드 중에서 마지막 카드의바로 아래 생성
            Folder folder = getFolderById(task.getFolder().getId());
            List<Card> cardList = task.getCards().stream().collect(Collectors.toList());
            Card maxPlacmentOrderCard = cardList.stream().max(Comparator.comparing(Card::getPlacementOrder)).get();
            Long maxPlacementOrder = maxPlacmentOrderCard.getPlacementOrder();
            Long placementOrder = maxPlacementOrder + 1;
            return placementOrder;
        }
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() >= MAX_FILE_SIZE) {
            throw new CustomException(ErrorStatus.FILE_SIZE_EXCEED);
        }
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
                .map(file -> FileResponseDto.of(file.getId(), file.getName(), file.getUrl(), file.getSize())).collect(Collectors.toList());
    }

    private Card getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_CARD)
        );

        if (card.isDeleted()) {
            throw new CustomException(ErrorStatus.DELETED_CARD);
        }
        return card;
    }

    private Folder getFolderById(Long folderId) {
        return folderRepository.findById(folderId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_FOLDER));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.NOT_FOUND_CATEGORY));
    }

    private void validatePdf(MultipartFile file) {
        if (!file.getContentType().equals(PDF_TYPE)) {
            throw new CustomException(ErrorStatus.NOT_PDF_FILE_TYPE);
        }
    }

    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new EntityNotFoundException(ErrorStatus.INVALID_TASK));
    }


    private InputStream getInputStream(MultipartFile file) throws IOException {
        return file.getInputStream();
    }

    private String getFoldername() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date).replace("-", "/") + "/";
    }
}
