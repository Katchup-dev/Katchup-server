package site.katchup.katchupserver.api.screenshot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.katchup.katchupserver.api.file.domain.File;
import site.katchup.katchupserver.api.member.domain.Member;
import site.katchup.katchupserver.api.member.repository.MemberRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.screenshot.dto.request.ScreenshotCreateRequestDto;
import site.katchup.katchupserver.api.screenshot.dto.response.ScreenshotGetPreSignedResponseDto;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.common.util.S3Util;

import java.util.HashMap;
import java.util.Optional;
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
    public ScreenshotGetPreSignedResponseDto getPreSignedUrl(Long memberId, String screenshotName) {
        String screenshotUploadPrefix = makeUploadPrefix(memberId);
        HashMap<String, String> preSignedUrlInfo = s3Util.generatePreSignedUrl(screenshotUploadPrefix, screenshotName);

        return ScreenshotGetPreSignedResponseDto.of(preSignedUrlInfo.get(s3Util.KEY_FILENAME)
                , preSignedUrlInfo.get(s3Util.KEY_PRESIGNED_URL), screenshotName, preSignedUrlInfo.get(s3Util.KEY_FILE_UPLOAD_DATE));
    }

    @Override
    @Transactional
    public String findUrl(Long memberId, ScreenshotCreateRequestDto requestDto) {
        return s3Util.findUrlByName(createKey(memberId, requestDto.getScreenshotUploadDate(), requestDto.getScreenshotUUID().toString()
        , requestDto.getScreenshotName()));
    }

    @Override
    @Transactional
    public String createKey(Long memberId, String screenshotDate, String screenshotUUID, String screenshotName) {
        String screenshotUploadPrefix = makeUploadPrefix(memberId);
        return screenshotUploadPrefix + "/" + screenshotDate + "/" + screenshotUUID + screenshotName;
    }

    @Override
    @Transactional
    public void deleteFile(Long memberId, String screenshotName, String screenshotUploadDate, String screenshotUUID) {
        Optional<Screenshot> screenshot = screenshotRepository.findById(UUID.fromString(screenshotUUID));
        if (screenshot.isPresent()) {
            screenshotRepository.delete(screenshot.get());
        } s3Util.deleteFile(createKey(memberId, screenshotUploadDate, screenshotUUID, screenshotName));
    }

    private String makeUploadPrefix(Long memberId) {
        Member member = memberRepository.findByIdOrThrow(memberId);
        String userUUID = member.getUserUUID();
        return s3Util.makeUploadPrefix(userUUID, SCREENSHOT_FOLDER_NAME);
    }

}
