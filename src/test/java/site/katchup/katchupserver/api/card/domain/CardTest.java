package site.katchup.katchupserver.api.card.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;

@DataJpaTest
public class CardTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TaskRepository taskRepository;

    @DisplayName("업무 카드 저장 테스트")
    @Test
    void successSaveCard() {
        // given
        Category category = Category.builder().name("Test Category").build();
        Folder folder = Folder.builder().category(category).name("Test Folder").build();
        Task task = Task.builder().folder(folder).name("Test Task").build();
        taskRepository.save(task);

        Card card = Card.builder()
                .index(1L)
                .content("Test Content")
                .note("Test Note")
                .isDeleted(false)
                .deletedAt(LocalDateTime.now())
                .task(task)
                .build();

        // when
        cardRepository.save(card);

        // then
        Card savedCard = cardRepository.findById(card.getId()).orElseThrow();
        assertThat(savedCard.getIndex()).isEqualTo(1L);
        assertThat(savedCard.getContent()).isEqualTo("Test Content");
        assertThat(savedCard.getNote()).isEqualTo("Test Note");
        assertThat(savedCard.isDeleted()).isFalse();
        assertThat(savedCard.getTask().getName()).isEqualTo("Test Task");
    }
}