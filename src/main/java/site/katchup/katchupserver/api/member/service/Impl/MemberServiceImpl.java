package site.katchup.katchupserver.api.member.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.dto.request.MemberProfileUpdateRequestDto;
import site.katchup.katchupserver.api.member.dto.response.MemberProfileGetResponseDto;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.member.service.MemberService;
import site.katchup.katchupserver.common.exception.BadRequestException;
import site.katchup.katchupserver.common.exception.InternalServerException;
import site.katchup.katchupserver.common.response.ErrorCode;
import site.katchup.katchupserver.external.s3.S3Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private static final String PROFILE_IMAGE_FOLDER_NAME = "profiles";
    private static final List<String> VALID_IMAGE_CONTENT_TYPE =  List.of("image/jpeg", "image/jpg", "image/png", "image/gif", "image/tiff", "image/tif");
    private static final long MAX_PROFILE_IMAGE_SIZE = 5 * 1024 * 1024;

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
            if (Objects.nonNull(profileImage)) {
                validateExtension(profileImage);
                validateFileSize(profileImage);
                String key = s3Service.uploadImage(s3Service.makeUploadPrefix(member.getUserUUID(), PROFILE_IMAGE_FOLDER_NAME), profileImage);
                member.updateImageUrl(s3Service.findUrlByName(key));
            }
            member.updateIntroduction(request.getIntroduction());
            member.updateNickname(request.getNickname());
        } catch (IOException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void validateFileSize(MultipartFile profileImage) {
        if (profileImage.getSize() > MAX_PROFILE_IMAGE_SIZE) {
            throw new BadRequestException(ErrorCode.INVALID_PROFILE_IMAGE_SIZE);
        }
    }

    private void validateExtension(MultipartFile profileImage) {
        if (!VALID_IMAGE_CONTENT_TYPE.contains(profileImage.getContentType())) {
            throw new BadRequestException(ErrorCode.INVALID_PROFILE_IMAGE_TYPE);
        }
    }
}