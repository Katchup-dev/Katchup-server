package site.katchup.katchupserver.api.keyword.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@Table(name = "card_keyword")
@NoArgsConstructor(access = PROTECTED)
public class CardKeyword {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public CardKeyword(Long id, Card card, Keyword keyword) {
        this.id = id;
        this.card = card;
        this.keyword = keyword;
    }
}
