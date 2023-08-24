package site.katchup.katchupserver.api.file.service;


import site.katchup.katchupserver.api.file.dto.request.FileCreateRequestDto;
import site.katchup.katchupserver.api.file.dto.request.FileGetPreSignedRequestDto;
import site.katchup.katchupserver.api.file.dto.response.FileGetPreSignedResponseDto;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;

public interface FileService {
    FileGetPreSignedResponseDto getFilePreSignedUrl(Long memberId, FileGetPreSignedRequestDto requestDto);

    String findUrl(Long memberId, FileCreateRequestDto requestDto);
}
