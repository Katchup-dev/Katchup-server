package site.katchup.katchupserver.api.member.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.katchup.katchupserver.common.domain.BaseEntity;

import java.nio.ByteBuffer;
import java.util.Objects;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    MemberProfile memberProfile;

    private String email;

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
        // TODO: default introduction 어떻게 할지에 따라 수정
        this.memberProfile = new MemberProfile(nickname, "", imageUrl);
        this.email = email;
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

    public void updateIntroduction(String introduction) {
        if (introduction != null) {
            this.memberProfile.updateIntroduction(introduction);
        }
    }

    public void updateNickname(String nickname) {
        if (nickname != null) {
            this.memberProfile.updateNickname(nickname);
        }
    }

    public void updateImageUrl(String imageUrl) {
        this.memberProfile.updateImageUrl(imageUrl);
    }

    public void clearRefreshToken() {
        this.refreshToken = null;
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

