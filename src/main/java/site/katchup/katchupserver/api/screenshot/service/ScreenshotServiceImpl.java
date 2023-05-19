package site.katchup.katchupserver.api.screenshot.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.katchup.katchupserver.api.card.domain.Card;
import site.katchup.katchupserver.api.card.repository.CardRepository;
import site.katchup.katchupserver.api.screenshot.domain.Screenshot;
import site.katchup.katchupserver.api.screenshot.dto.response.UploadScreenshotResponseDTO;
import site.katchup.katchupserver.api.screenshot.repository.ScreenshotRepository;
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
public class ScreenshotServiceImpl implements ScreenshotService{

    private static final String SCREENSHOT_FOLDER_NAME = "screenshots";

    private final S3Util s3Util;

    private final ScreenshotValidator screenshotValidator;

    private final ScreenshotRepository screenshotRepository;

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public UploadScreenshotResponseDTO uploadScreenshot(MultipartFile file, Long cardId) {
        screenshotValidator.validate(file);
        final String imageId = getUUIDFileName();
        String uploadFilePath = SCREENSHOT_FOLDER_NAME + "/" + getFoldername();
        String uploadFileName = uploadFilePath + "/" + imageId + extractExtension(file);

        try {
            String uploadImageUrl = s3Util.upload(getInputStream(file), uploadFileName, getObjectMetadata(file));
            Screenshot screenshot =  Screenshot.builder()
                    .id(UUID.fromString(imageId))
                    .url(uploadImageUrl)
                    .card(getCardById(cardId))
                    .build();

            screenshotRepository.save(screenshot);

            return UploadScreenshotResponseDTO.builder()
                    .id(screenshot.getId().toString())
                    .screenshotUrl(screenshot.getUrl())
                    .build();

        } catch (IOException e) {
            throw new CustomException(ErrorStatus.IMAGE_UPLOAD_EXCEPTION);
        }
    }


    private String getUUIDFileName() {
        return UUID.randomUUID().toString();
    }

    private Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("해당 카드를 찾을 수 없습니다."));
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
