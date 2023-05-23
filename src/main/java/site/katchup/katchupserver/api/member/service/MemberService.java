package site.katchup.katchupserver.api.member.service;

import site.katchup.katchupserver.api.member.dto.MemberResponseDto;

public interface MemberService {
    MemberResponseDto getMemberProfile(Long memberId);
}
