package site.katchup.katchupserver.api.file.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class File extends BaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String changedName;

    @Column(nullable = false, name = "file_key")
    private String fileKey;

    @Column(nullable = false)
    private String uploadDate;

    @Column(nullable = false)
    private int size;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public File(UUID id, String originalName, String changedName, String fileKey,
                String uploadDate, int size, Card card) {
        this.id = id;
        this.originalName = originalName;
        this.changedName = changedName;
        this.fileKey = fileKey;
        this.uploadDate = uploadDate;
        this.card = card;
        this.size = size;
    }
}
