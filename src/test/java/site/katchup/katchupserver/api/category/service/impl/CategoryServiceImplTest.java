package site.katchup.katchupserver.api.category.service.impl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.category.repository.CategoryRepository;
import site.katchup.katchupserver.api.category.service.Impl.CategoryServiceImpl;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.folder.repository.FolderRepository;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.api.task.repository.TaskRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void 성공_Category_삭제() throws Exception {
    //given
        Category category = Category.builder()
                .name("test")
                .build();

        Folder folder = Folder.builder()
                .name("test")
                .category(category)
                .build();

        Task task = Task.builder()
                .name("test")
                .folder(folder)
                .build();

        Card card1 = Card.builder()
                .task(task)
                .placementOrder(1L)
                .note("test note")
                .content("test content")
                .isDeleted(false)
                .build();

        Card card2 = Card.builder()
                .task(task)
                .placementOrder(2L)
                .note("test note2")
                .isDeleted(false)
                .build();
        categoryRepository.save(category);
        folderRepository.save(folder);
        taskRepository.save(task);
        cardRepository.save(card1);
        cardRepository.save(card2);
    //when
        categoryService.deleteCategory(category.getId());
    //then
        assertThat(categoryRepository.findAll()).isEmpty();
        assertThat(card1.isDeleted()).isEqualTo(true);
        assertThat(card2.isDeleted()).isEqualTo(true);

    }



}
