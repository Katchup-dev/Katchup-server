package site.katchup.katchupserver.api.screenshot.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.api.task.domain.Task;

import java.util.UUID;

@DataJpaTest
class ScreenshotTest {

    @Autowired
    private ScreenshotRepository screenshotRepository;

    @DisplayName("스크린샷 저장 테스트")
    @Test
    public void successSaveScreenshot() {
        // Given
        Category category = Category.builder().name("Test Category").build();
        Task task = Task.builder().category(category).name("Test Task").build();
        SubTask subTask = SubTask.builder().task(task).name("Test SubTask").build();
        Card card = Card.builder().placementOrder(1L).subTask(subTask).build();

        UUID uuid = UUID.randomUUID();

        Screenshot screenshot = Screenshot.builder()
                .id(uuid)
                .build();

        // When
        Screenshot savedScreenshot = screenshotRepository.save(screenshot);

        // Then
        Assertions.assertNotNull(savedScreenshot.getId());
        Assertions.assertEquals(savedScreenshot.getId(), uuid);
    }
}