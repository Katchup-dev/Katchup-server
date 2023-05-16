package site.katchup.katchupserver.api.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.dto.MemberResponseDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.response.ErrorStatus;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto getMemberProfile(Long memberId) {
        Member member = findMember(memberId);

        return new MemberResponseDto(member.getImageUrl(), member.getNickname(), member.getEmail());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.INVALID_MEMBER));
    }
}
