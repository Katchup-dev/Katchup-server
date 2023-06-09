package site.katchup.katchupserver.api.screenshot.service.impl;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.common.CardProvider;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDto;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotService;
import site.katchup.katchupserver.api.screenshot.service.ScreenshotValidator;
import site.katchup.katchupserver.common.exception.CustomException;
import site.katchup.katchupserver.common.response.ErrorStatus;
import site.katchup.katchupserver.common.util.S3Util;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScreenshotServiceImpl implements ScreenshotService {

    private static final String SCREENSHOT_FOLDER_NAME = "screenshots";

    private final S3Util s3Util;

    private final ScreenshotValidator screenshotValidator;

    private final ScreenshotRepository screenshotRepository;

    private final CardProvider cardProvider;

    @Override
    @Transactional
    public UploadScreenshotResponseDto uploadScreenshot(MultipartFile file, Long cardId) {
        screenshotValidator.validate(file);
        final String imageId = getUUIDFileName();
        String uploadFilePath = SCREENSHOT_FOLDER_NAME + "/" + getFoldername();
        String uploadFileName = uploadFilePath + "/" + imageId + extractExtension(file);

        try {
            String uploadImageUrl = s3Util.upload(getInputStream(file), uploadFileName, getObjectMetadata(file));
            Card card = cardProvider.getCardById(cardId);
            Screenshot screenshot =  Screenshot.builder()
                    .id(UUID.fromString(imageId))
                    .url(uploadImageUrl)
                    .card(cardProvider.getCardById(cardId))
                    .build();

            screenshotRepository.save(screenshot);

            return UploadScreenshotResponseDto.builder()
                    .id(screenshot.getId().toString())
                    .screenshotUrl(screenshot.getUrl())
                    .build();

        } catch (Exception e) {
            throw new CustomException(ErrorStatus.IMAGE_UPLOAD_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public void delete(Long cardId, String screenshotId) {
        screenshotRepository.deleteById(UUID.fromString(screenshotId));
    }

    
    private String getUUIDFileName() {
        return UUID.randomUUID().toString();
    }

    private String getFoldername() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date).replace("-", "/");
    }

    private ObjectMetadata getObjectMetadata(MultipartFile file) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        return objectMetadata;
    }

    private InputStream getInputStream(MultipartFile file) throws IOException {
        return file.getInputStream();
    }

    private String extractExtension(MultipartFile file) {
        try {
            return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new CustomException(ErrorStatus.IMAGE_UPLOAD_EXCEPTION);
        }
    }
}
