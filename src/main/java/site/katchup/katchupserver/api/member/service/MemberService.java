package site.katchup.katchupserver.api.member.service;

import site.katchup.katchupserver.api.member.dto.MemberProfileGetResponseDto;

public interface MemberService {

    MemberProfileGetResponseDto getMemberProfile(Long memberId);

}
