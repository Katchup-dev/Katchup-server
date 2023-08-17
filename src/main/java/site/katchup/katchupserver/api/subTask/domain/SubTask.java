package site.katchup.katchupserver.api.subTask.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
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
public class SubTask extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "subTask", cascade = ALL)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public SubTask(String name, Task task) {
        this.name = name;
        this.task = task;
        this.task.addSubTask(this);
        this.isDeleted = false;
    }

    public void deleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    public void addCard(Card card) { cards.add(card); }
}
