package site.katchup.katchupserver.api.member.service;

import site.katchup.katchupserver.api.member.dto.request.MemberDeleteRequestDto;

public interface WithdrawService {
    void deleteMember(Long memberId, MemberDeleteRequestDto memberDeleteRequestDto);
}
