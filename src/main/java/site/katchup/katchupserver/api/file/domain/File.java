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
    private String name;

    @Column(nullable = false, name = "file_key")
    private String fileKey;

    @Column(nullable = false)
    private int size;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public File(UUID id, String name, String fileKey, int size, Card card) {
        this.id = id;
        this.name = name;
        this.fileKey = fileKey;
        this.card = card;
        this.size = size;
    }

    public void updateFileName (String newFileName) {
        this.name = newFileName;
    }
}
