package site.katchup.katchupserver.api.task.domain;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Card> cards = new ArrayList<>();

    @Builder
    public Task(String name, Folder folder) {
        this.name = name;
        this.folder = folder;
        this.folder.addTask(this);
    }

    public void addCard(Card card) { cards.add(card); }
}
