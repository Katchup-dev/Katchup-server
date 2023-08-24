package site.katchup.katchupserver.api.file.service;


import site.katchup.katchupserver.api.file.dto.request.FileGetPreSignedRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetPreSignedResponseDto;

public interface FileService {
    FileGetPreSignedResponseDto getFilePreSignedUrl(Long memberId, FileGetPreSignedRequestDto requestDto);

    void deleteFile(String fileId);
}
