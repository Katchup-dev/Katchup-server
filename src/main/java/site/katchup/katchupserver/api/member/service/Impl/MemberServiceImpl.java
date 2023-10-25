package site.katchup.katchupserver.api.member.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.domain.MemberProfile;
import site.katchup.katchupserver.api.member.dto.request.MemberProfileUpdateRequestDto;
import site.katchup.katchupserver.api.member.dto.response.MemberProfileGetResponseDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.member.service.MemberService;
import site.katchup.katchupserver.common.exception.InternalServerException;
import site.katchup.katchupserver.common.response.ErrorCode;
import site.katchup.katchupserver.external.s3.S3Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private static final String PROFILE_IMAGE_PREFIX = "profiles";

    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    @Override
    public MemberProfileGetResponseDto getMemberProfile(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        return MemberProfileGetResponseDto.of(member);
    }

    @Override
    @Transactional
    public void updateMemberProfile(Long memberId, MultipartFile profileImage, MemberProfileUpdateRequestDto request) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        try {
            String key = s3Service.uploadImage( s3Service.makeUploadPrefix(member.getUserUUID(), PROFILE_IMAGE_PREFIX) , profileImage);
            member.updateMemberProfile(MemberProfile.builder()
                    .nickname(request.getNickname())
                    .introduction(request.getIntroduction())
                    .imageUrl(s3Service.findUrlByName(key))
                    .build());
        } catch (IOException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

}
