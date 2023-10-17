package site.katchup.katchupserver.api.category.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.member.domain.Member;
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
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_shared", nullable = false)
    private boolean isShared;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "category", cascade = ALL)
    private List<Task> tasks = new ArrayList<>();

    @Builder
    public Category(String name, boolean isShared, Member member) {
        this.name = name;
        this.isShared = isShared;
        this.member = member;
        this.isDeleted = false;
    }

    public void deleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void updateCategoryName(String newName) {
        this.name = newName;
    }

    public void toggleSharedStatus() {
        isShared = !isShared;
    }
}
