package site.katchup.katchupserver.api.file.service;


import site.katchup.katchupserver.api.file.dto.request.FileCreateRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetDownloadPreSignedResponseDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetUploadPreSignedResponseDto;

public interface FileService {

    FileGetDownloadPreSignedResponseDto getDownloadPreSignedUrl(String filePath, String fileName);

    String createKey(Long memberId, String fileDate, String fileUUID, String fileName);

    void deleteFile(Long memberId, String fileOriginalName, String fileUploadDate, String fileUUID);

    FileGetUploadPreSignedResponseDto getUploadPreSignedUrl(Long memberId, String fileName);

}
