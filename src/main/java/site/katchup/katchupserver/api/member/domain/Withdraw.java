package site.katchup.katchupserver.api.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import site.katchup.katchupserver.common.util.StringListConverter;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Withdraw {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 2000)
    @Convert(converter = StringListConverter.class)
    private List<String> reason;

    @CreatedDate
    private LocalDateTime expectedDeleteAt;

    @Builder
    public Withdraw(Member member, List<String> reason) {
        this.member = member;
        this.reason = reason;
        this.expectedDeleteAt = now().plusDays(60);
    }
}
