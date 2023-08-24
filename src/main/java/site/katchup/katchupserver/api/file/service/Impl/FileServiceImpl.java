package site.katchup.katchupserver.api.file.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.file.dto.request.FileGetPreSignedRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetPreSignedResponseDto;
import site.katchup.katchupserver.api.file.service.FileService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.common.util.S3Util;

import java.util.HashMap;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String FILE_FOLDER_NAME = "files";

    private final S3Util s3Util;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public FileGetPreSignedResponseDto getFilePreSignedUrl(Long memberId, FileGetPreSignedRequestDto requestDto) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        String userUUID = member.getUserUUID();
        String fileUploadPrefix = s3Util.makeUploadPrefix(userUUID, FILE_FOLDER_NAME);
        HashMap<String, String> preSignedUrlInfo = s3Util.generatePreSignedUrl(fileUploadPrefix, requestDto.getFileName());
        return FileGetPreSignedResponseDto.of(preSignedUrlInfo.get(s3Util.KEY_FILENAME), requestDto.getFileName(), preSignedUrlInfo.get(s3Util.KEY_PRESIGNED_URL));
    }

}
