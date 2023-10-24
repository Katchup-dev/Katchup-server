package site.katchup.katchupserver.api.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.katchup.katchupserver.api.member.repository.MemberRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 저장 테스트")
    void successSaveMember() {
        // given
        Member member = Member.builder()
                .nickname("캐쳐비")
                .email("katchup@example.com")
                .imageUrl("https://example.com/katchup.png")
                .isDeleted(false)
                .isNewUser(false)
                .refreshToken("abcde12345")
                .build();

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getMemberProfile().getNickname()).isEqualTo(member.getMemberProfile().getNickname());
        assertThat(savedMember.getEmail()).isEqualTo(member.getEmail());
        assertThat(savedMember.getMemberProfile().getImageUrl()).isEqualTo(member.getMemberProfile().getImageUrl());
        assertThat(savedMember.isDeleted()).isEqualTo(member.isDeleted());
        assertThat(savedMember.isNewUser()).isEqualTo(member.isNewUser());
        assertThat(savedMember.getRefreshToken()).isEqualTo(member.getRefreshToken());
    }
}