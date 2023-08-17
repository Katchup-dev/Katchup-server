package site.katchup.katchupserver.api.task.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
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
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "task", cascade = ALL)
    private List<SubTask> subTasks = new ArrayList<>();

    @Builder
    public Task(String name, Category category) {
        this.name = name;
        this.category = category;
        this.category.addTask(this);
        this.isDeleted = false;
    }

    public void deleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void addSubTask(SubTask task) {
        subTasks.add(task);
    }

    public void updateTaskName(String taskName) {
        this.name = taskName;
    }
}
