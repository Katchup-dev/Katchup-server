package site.katchup.katchupserver.api.screenshot.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.sticker.domain.Sticker;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Screenshot extends BaseEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false, name = "screenshot_key")
    private String screenshotKey;

    @Column(nullable = false)
    private String uploadDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @OneToMany(mappedBy = "screenshot", cascade = ALL)
    private List<Sticker> sticker = new ArrayList<>();

    @Builder
    public Screenshot(UUID id, String url, String screenshotKey, String uploadDate, Card card) {
        this.id = id;
        this.url = url;
        this.screenshotKey =screenshotKey;
        this.uploadDate = uploadDate;
        this.card = card;
    }
}
