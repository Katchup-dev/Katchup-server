package site.katchup.katchupserver.api.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;

@Entity
@Table(name = "task_keyword")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Builder
    public TaskKeyword(Long id, Card card, Keyword keyword) {
        this.id = id;
        this.card = card;
        this.keyword = keyword;
    }
}
