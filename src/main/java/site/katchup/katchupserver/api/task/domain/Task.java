package site.katchup.katchupserver.api.task.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.folder.domain.Folder;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "task", cascade = ALL)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public Task(String name, Folder folder) {
        this.name = name;
        this.folder = folder;
        this.folder.addTask(this);
    }

    public void deleted() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    public void addCard(Card card) { cards.add(card); }
}
