package site.katchup.katchupserver.api.card.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Double size;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @Builder
    public File(Long id, String name, String url, Double size, Card card) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.card = card;
        this.size = size;
        this.card.addFile(this);
    }

    @Builder
    public File(String name, String url, Double size, Card card) {
        this.name = name;
        this.url = url;
        this.size = size;
        this.card = card;
    }
}
