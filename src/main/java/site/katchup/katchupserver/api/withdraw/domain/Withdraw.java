package site.katchup.katchupserver.api.withdraw.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    }
}
