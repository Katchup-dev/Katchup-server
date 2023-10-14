package site.katchup.katchupserver.api.member.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.domain.Withdraw;
import site.katchup.katchupserver.api.member.dto.request.MemberDeleteRequestDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.member.repository.WithdrawRepository;
import site.katchup.katchupserver.api.member.service.WithdrawService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WithdrawServiceImpl implements WithdrawService {

    private final WithdrawRepository withdrawRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void deleteMember(Long memberId, MemberDeleteRequestDto memberDeleteRequestDto) {
        Withdraw withdraw = Withdraw.builder()
                .member(memberRepository.findByIdOrThrow(memberId))
                .reason(memberDeleteRequestDto.getReason())
                .build();
        withdrawRepository.save(withdraw);

        Member member = memberRepository.findByIdOrThrow(memberId);
        member.deleted();
    }
}
