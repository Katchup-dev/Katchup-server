package site.katchup.katchupserver.api.file.service;


import site.katchup.katchupserver.api.file.dto.request.FileCreateRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetDownloadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetUploadPreSignedResponseDto;

public interface FileService {

    FileGetDownloadPreSignedResponseDto getDownloadPreSignedUrl(String filePath, String fileName);

    String createKey(Long memberId, FileCreateRequestDto requestDto);

    void deleteFile(String fileId);

    FileGetUploadPreSignedResponseDto getUploadPreSignedUrl(Long memberId, String fileName);

}
