package site.katchup.katchupserver.api.screenshot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Screenshot extends BaseEntity {
    @Id
    private UUID id;

    @Column(name="sticker_order", columnDefinition = "integer default 0")
    private int stickerOrder;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Screenshot(UUID id, String url, Card card) {
        this.id = id;
        this.stickerOrder = 0;
        this.url = url;
        this.card = card;
    }
}
