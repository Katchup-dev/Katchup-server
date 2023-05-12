package site.katchup.katchupserver.api.task.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.task.repository.TaskRepository;

@DataJpaTest
public class TaskTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private FolderRepository folderRepository;

    @DisplayName("Task 엔티티 저장 테스트")
    @Test
    void testSaveTask() {
        // given
        Folder folder = Folder.builder().name("Test Folder").build();
        folderRepository.save(folder);

        Task task = Task.builder()
                .index(1L)
                .name("Test Task")
                .content("Test Content")
                .note("Test Note")
                .isDeleted(false)
                .deletedAt(LocalDateTime.now())
                .folder(folder)
                .build();

        // when
        taskRepository.save(task);

        // then
        Task savedTask = taskRepository.findById(task.getId()).orElseThrow();
        assertThat(savedTask.getIndex()).isEqualTo(1L);
        assertThat(savedTask.getName()).isEqualTo("Test Task");
        assertThat(savedTask.getContent()).isEqualTo("Test Content");
        assertThat(savedTask.getNote()).isEqualTo("Test Note");
        assertThat(savedTask.isDeleted()).isFalse();
        assertThat(savedTask.getDeletedAt()).isEqualTo(LocalDateTime.now());
        assertThat(savedTask.getFolder().getName()).isEqualTo("Test Folder");
    }
}