package site.katchup.katchupserver.api.member.service;

import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.member.dto.request.MemberProfileUpdateRequestDto;
import site.katchup.katchupserver.api.member.dto.response.MemberProfileGetResponseDto;

public interface MemberService {

    MemberProfileGetResponseDto getMemberProfile(Long memberId);
    void updateMemberProfile(Long memberId, MultipartFile profileImage, MemberProfileUpdateRequestDto request);

}
