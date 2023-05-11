package site.katchup.katchupserver.api.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.common.domain.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String memo;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "is_new_user", nullable = false)
    private boolean isNewUser;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public Member(String nickname, String memo, String imageUrl, boolean isDeleted, boolean isNewUser, String refreshToken) {
        this.nickname = nickname;
        this.memo = memo;
        this.imageUrl = imageUrl;
        this.isDeleted = isDeleted;
        this.isNewUser = isNewUser;
        this.refreshToken = refreshToken;
    }
}
