package site.katchup.katchupserver.api.screenshot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotGetPreSignedRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.common.util.S3Util;

import java.util.HashMap;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScreenshotServiceImpl implements ScreenshotService {

    private static final String SCREENSHOT_FOLDER_NAME = "screenshots";

    private final S3Util s3Util;
    private final ScreenshotRepository screenshotRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public ScreenshotGetPreSignedResponseDto getScreenshotPreSignedUrl(Long memberId, ScreenshotGetPreSignedRequestDto requestDto) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        String userUUID = member.getUserUUID();
        String screenshotUploadPrefix = s3Util.makeUploadPrefix(userUUID, SCREENSHOT_FOLDER_NAME);
        HashMap<String, String> preSignedUrlInfo = s3Util.generatePreSignedUrl(screenshotUploadPrefix, requestDto.getScreenshotName());
        return ScreenshotGetPreSignedResponseDto.of(preSignedUrlInfo.get(s3Util.KEY_FILENAME), preSignedUrlInfo.get(s3Util.KEY_PRESIGNED_URL));
    }

    @Override
    @Transactional
    public void deleteScreenshot(Long cardId, String screenshotId) {
        screenshotRepository.deleteById(UUID.fromString(screenshotId));
    }

}
