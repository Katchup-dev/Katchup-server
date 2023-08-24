package site.katchup.katchupserver.api.card.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.file.domain.File;
import site.katchup.katchupserver.api.link.domain.Link;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.subTask.domain.SubTask;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long placementOrder;

    private String content;

    @Column(length = 200)
    private String note;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sub_task_id")
    private SubTask subTask;

    @OneToMany(mappedBy = "card", cascade = ALL)
    private List<Screenshot> screenshots = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = ALL)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = ALL)
    private List<Link> links = new ArrayList<>();

    @Builder
    public Card(Long placementOrder, String content, String note, SubTask subTask) {
        this.placementOrder = placementOrder;
        this.content = content;
        this.isDeleted = false;
        this.note = note;
        this.subTask = subTask;
        this.subTask.addCard(this);
    }

    public void deletedCard() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
    
    public void plusPlacementOrder() {
        this.placementOrder += 1;
    }
}
