package site.katchup.katchupserver.api.file.service;


import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.file.dto.request.FileCreateRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetPreSignedResponseDto;

public interface FileService {

    String createKey(Long memberId, FileCreateRequestDto requestDto);

    void deleteFile(String fileId);

    FileGetPreSignedResponseDto getFilePreSignedUrl(Long memberId, String fileName);

    String findUrl(Long memberId, FileCreateRequestDto requestDto);

}
