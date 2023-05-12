package site.katchup.katchupserver.api.link.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Link extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(name = "start_index", nullable = false)
    private int startIndex;

    @Column(name = "end_index", nullable = false)
    private int endIndex;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public Link(Long id, String url, Card card, int startIndex, int endIndex) {
        this.id = id;
        this.url = url;
        this.card = card;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.card.addLink(this);
    }
}
