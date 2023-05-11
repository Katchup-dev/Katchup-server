package site.katchup.katchupserver.api.folder.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.category.domain.Category;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Folder(Long id, String name, boolean isDeleted, LocalDateTime deletedAt, Category category) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
        this.deletedAt = deletedAt;
        this.category = category;
    }
}
