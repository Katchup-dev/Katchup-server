package site.katchup.katchupserver.api.screenshot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Screenshot extends BaseEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private int index;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Screenshot(int index, String url, Card card) {
        this.id = randomUUID();
        this.index = index;
        this.url = url;
        this.card = card;
        this.card.addScreenshot(this);
    }
}
