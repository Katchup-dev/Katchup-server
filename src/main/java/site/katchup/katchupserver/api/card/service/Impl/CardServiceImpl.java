package site.katchup.katchupserver.api.card.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.dto.CardDeleteRequestDto;
import site.katchup.katchupserver.api.card.dto.CardResponseDto;
import site.katchup.katchupserver.api.card.service.CardService;
import site.katchup.katchupserver.api.common.CardProvider;
import site.katchup.katchupserver.api.keyword.dto.KeywordResponseDto;
import site.katchup.katchupserver.api.keyword.repository.CardKeywordRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;
import site.katchup.katchupserver.api.trash.domain.Trash;
import site.katchup.katchupserver.api.trash.repository.TrashRepository;

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
}
