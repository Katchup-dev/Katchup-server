package site.katchup.katchupserver.api.task.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.folder.domain.Folder;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.time.LocalDateTime;
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
    private Long index;

    @Column(nullable = false)
    private String name;

    @Column
    private String content;

    @Column
    private String note;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "folder_id")
    private Folder folder;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Screenshot> screenshots = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Link> links = new ArrayList<>();


    @Builder
    public Task(Long index, String name, String content, String note, boolean isDeleted, LocalDateTime deletedAt, Folder folder) {
        this.index = index;
        this.name = name;
        this.content = content;
        this.note = note;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.folder = folder;
        this.folder.addTask(this);
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
