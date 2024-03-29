package site.katchup.katchupserver.api.file.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.file.domain.File;
import site.katchup.katchupserver.api.file.dto.response.FileGetDownloadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetUploadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.repository.FileRepository;
import site.katchup.katchupserver.api.file.service.FileService;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.external.s3.PreSignedUrlVO;
import site.katchup.katchupserver.external.s3.S3Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private static final String FILE_FOLDER_NAME = "files";

    private final S3Service s3Service;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public FileGetUploadPreSignedResponseDto getUploadPreSignedUrl(Long memberId, String fileName) {
        String fileUploadPrefix = makeUploadPrefix(memberId);
        PreSignedUrlVO preSignedUrlInfo = s3Service.getUploadPreSignedUrl(fileUploadPrefix, fileName);

        return FileGetUploadPreSignedResponseDto.of(preSignedUrlInfo.getFileName(), fileName,
                preSignedUrlInfo.getPreSignedUrl(), preSignedUrlInfo.getFileUploadDate());
    }

    @Override
    @Transactional
    public FileGetDownloadPreSignedResponseDto getDownloadPreSignedUrl(String fileUUID, String fileName) {
        File findFile = fileRepository.findByIdOrThrow(UUID.fromString(fileUUID));
        String downloadUrl = s3Service.getDownloadPreSignedUrl(findFile.getFileKey(), fileName);
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
        file.ifPresent(fileRepository::delete);
        s3Service.deleteFile(createKey(memberId, fileUploadDate, fileUUID, fileOriginalName));
    }
    
    private String makeUploadPrefix(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        String userUUID = member.getUserUUID();
        return s3Service.makeUploadPrefix(userUUID, FILE_FOLDER_NAME);
    }
}
