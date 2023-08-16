package site.katchup.katchupserver.api.card.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.subTask.repository.SubTaskRepository;
import site.katchup.katchupserver.api.task.domain.Task;

@DataJpaTest
public class CardTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @DisplayName("업무 카드 저장 테스트")
    @Test
    void successSaveCard() {
        // given
        Category category = Category.builder().name("Test Category").build();
        Task task = Task.builder().category(category).name("Test Task").build();
        SubTask subTask = SubTask.builder().task(task).name("Test SubTask").build();
        subTaskRepository.save(subTask);

        Card card = Card.builder()
                .placementOrder(1L)
                .content("Test Content")
                .note("Test Note")
                .subTask(subTask)
                .build();

        // when
        cardRepository.save(card);

        // then
        Card savedCard = cardRepository.findById(card.getId()).orElseThrow();
        assertThat(savedCard.getPlacementOrder()).isEqualTo(1L);
        assertThat(savedCard.getContent()).isEqualTo("Test Content");
        assertThat(savedCard.getNote()).isEqualTo("Test Note");
        assertThat(savedCard.getIsDeleted()).isFalse();
        assertThat(savedCard.getSubTask().getName()).isEqualTo("Test SubTask");
    }
}