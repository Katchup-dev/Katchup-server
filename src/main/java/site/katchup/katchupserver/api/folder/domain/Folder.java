package site.katchup.katchupserver.api.folder.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.task.domain.Task;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Folder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "folder", cascade = ALL)
    private List<Task> tasks = new ArrayList<>();

    @Builder
    public Folder(String name, boolean isDeleted, LocalDateTime deletedAt, Category category) {
        this.name = name;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.category = category;
        this.category.addFolder(this);
    }

    public void deleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateFolderName(String folderName) {
        this.name = folderName;
    }
}
