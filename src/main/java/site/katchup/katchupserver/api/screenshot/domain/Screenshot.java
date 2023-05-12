package site.katchup.katchupserver.api.screenshot.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Screenshot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int index;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Screenshot(Long id, int index, String url, Card card) {
        this.id = id;
        this.index = index;
        this.url = url;
        this.card = card;
        this.card.addScreenshot(this);
    }
}
