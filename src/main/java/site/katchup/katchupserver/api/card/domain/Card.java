package site.katchup.katchupserver.api.card.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.keyword.domain.Keyword;
import site.katchup.katchupserver.api.link.domain.Link;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.task.domain.Task;
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

    @Column(name = "placement_order", nullable = false)
    private Long placementOrder;

    @Column(length = 2000)
    private String content;

    @Column(length = 200)
    private String note;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @OneToMany(mappedBy = "card", cascade = ALL)
    private List<Screenshot> screenshots = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = ALL)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = ALL)
    private List<Link> links = new ArrayList<>();

    @Builder
    public Card(Long placementOrder, String content, String note, boolean isDeleted, LocalDateTime deletedAt, Task task) {
        this.placementOrder = placementOrder;
        this.content = content;
        this.note = note;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.task = task;
        this.task.addCard(this);
    }

    public void addScreenshot(Screenshot screenshot) {
        screenshots.add(screenshot);
    }

    public void addFile(File file) {
        files.add(file);
    }

    public void addLink(Link link) {
        links.add(link);
    }
}
