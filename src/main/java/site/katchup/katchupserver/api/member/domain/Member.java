package site.katchup.katchupserver.api.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.common.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "is_new_user", nullable = false)
    private boolean isNewUser;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Builder
    public Member(String nickname, String email, String imageUrl, boolean isDeleted, boolean isNewUser, String refreshToken) {
        this.nickname = nickname;
        this.email = email;
        this.imageUrl = imageUrl;
        this.isDeleted = isDeleted;
        this.isNewUser = isNewUser;
        this.refreshToken = refreshToken;
    }
}
