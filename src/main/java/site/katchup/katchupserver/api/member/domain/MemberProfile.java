package site.katchup.katchupserver.api.member.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class MemberProfile {

    @Column(nullable = false, length = 20)
    private String nickname;

    private String introduction;

    @Column(name = "image_url")
    private String imageUrl;


    @Builder
    public MemberProfile(String nickname, String introduction, String imageUrl) {
        this.nickname = nickname;
        this.introduction = introduction;
        this.imageUrl = imageUrl;
    }
}
