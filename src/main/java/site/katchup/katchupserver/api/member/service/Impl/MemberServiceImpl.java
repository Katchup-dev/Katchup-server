package site.katchup.katchupserver.api.member.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.dto.MemberProfileGetResponseDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.member.service.MemberService;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberProfileGetResponseDto getMemberProfile(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);

        return new MemberProfileGetResponseDto(member.getImageUrl(), member.getNickname(), member.getEmail());
    }

}
