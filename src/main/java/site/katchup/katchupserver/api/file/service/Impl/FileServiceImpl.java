package site.katchup.katchupserver.api.file.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.file.domain.File;
import site.katchup.katchupserver.api.file.dto.request.FileCreateRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetDownloadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetUploadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.repository.FileRepository;
import site.katchup.katchupserver.api.file.service.FileService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.common.util.S3Util;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String FILE_FOLDER_NAME = "files";

    private final S3Util s3Util;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileGetUploadPreSignedResponseDto getUploadPreSignedUrl(Long memberId, String fileName) {
        String fileUploadPrefix = makeUploadPrefix(memberId);
        HashMap<String, String> preSignedUrlInfo = s3Util.generatePreSignedUrl(fileUploadPrefix, fileName);

        return FileGetUploadPreSignedResponseDto.of(preSignedUrlInfo.get(s3Util.KEY_FILENAME), fileName
                , preSignedUrlInfo.get(s3Util.KEY_PRESIGNED_URL), preSignedUrlInfo.get(s3Util.KEY_FILE_UPLOAD_DATE));
    }

    @Override
    @Transactional
    public FileGetDownloadPreSignedResponseDto getDownloadPreSignedUrl(String fileUUID, String fileName) {
        File findFile = fileRepository.findByIdOrThrow(UUID.fromString(fileUUID));
        String downloadUrl = s3Util.getDownloadPreSignedUrl(findFile.getFileKey(), fileName);
        return FileGetDownloadPreSignedResponseDto.of(downloadUrl);
    }

    @Override
    @Transactional
    public String createKey(Long memberId, String fileDate, String fileUUID, String fileName) {
        String fileUploadPrefix = makeUploadPrefix(memberId);
        return fileUploadPrefix + "/" + fileDate + "/" + fileUUID + fileName;
    }

    @Override
    @Transactional
    public void deleteFile(Long memberId, String fileOriginalName, String fileUploadDate, String fileUUID) {
        Optional<File> file = fileRepository.findById(UUID.fromString(fileUUID));
        if (file.isPresent()) {
            fileRepository.delete(file.get());
        } s3Util.deleteFile(createKey(memberId, fileUploadDate, fileUUID, fileOriginalName));
    }
    
    private String makeUploadPrefix(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        String userUUID = member.getUserUUID();
        return s3Util.makeUploadPrefix(userUUID, FILE_FOLDER_NAME);
    }
}
