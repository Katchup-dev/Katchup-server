package site.katchup.katchupserver.api.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.nio.ByteBuffer;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "user_UUID", nullable = false)
    private String userUUID;

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
        updateUserUUID();
    }

    public void updateMemberStatus(boolean isNewUser, boolean isDeleted, String refreshToken) {
        this.isNewUser = isNewUser;
        this.isDeleted = isDeleted;
        this.refreshToken = refreshToken;
    }

    // 10자리의 katchup 유저 코드 생성
    private void updateUserUUID() {
        String uuid = java.util.UUID.randomUUID().toString();
        int l = ByteBuffer.wrap(uuid.getBytes()).getInt();
        this.userUUID = Integer.toString(l,9);
    }

    public void deleted() {
        this.isDeleted = true;
    }
}

