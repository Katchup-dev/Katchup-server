package site.katchup.katchupserver.api.sticker.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.common.domain.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Sticker extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "sticker_order")
    private Integer order;

    @Column(name = "x_coordinate")
    private Float x;

    @Column(name = "y_coordinate")
    private Float y;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "screenshot_id")
    private Screenshot screenshot;

    @Builder
    public Sticker(Integer order, Float x, Float y, Screenshot screenshot) {
        this.order = order;
        this.x = x;
        this.y = y;
        this.screenshot = screenshot;
    }
}