package site.katchup.katchupserver.api.member.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.dto.MemberProfileGetResponseDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.member.service.MemberService;
import site.katchup.katchupserver.common.exception.UnauthorizedException;
import site.katchup.katchupserver.common.response.ErrorCode;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberProfileGetResponseDto getMemberProfile(Long memberId) {
        Member member = findMember(memberId);

        return new MemberProfileGetResponseDto(member.getImageUrl(), member.getNickname(), member.getEmail());
    }

    private Member findMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new UnauthorizedException(ErrorCode.INVALID_MEMBER));
    }
}
