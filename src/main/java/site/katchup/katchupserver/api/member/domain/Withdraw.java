package site.katchup.katchupserver.api.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Withdraw {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String reason;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    public Withdraw(Member member, String reason) {
        this.member = member;
        this.reason = reason;
        this.createdAt = now();
    }
}
