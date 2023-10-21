package site.katchup.katchupserver.external.s3;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.katchup.katchupserver.common.exception.InternalServerException;
import site.katchup.katchupserver.common.response.ErrorCode;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class S3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.region.static}")
    private String regionString;
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @PostConstruct
    public void setEnv() {
        System.setProperty("aws.accessKeyId", accessKey);
        System.setProperty("aws.secretAccessKey", secretKey);
    }

    private final Region region = Region.of(regionString);

    private final S3Presigner preSigner = S3Presigner.builder()
            .region(region)
            .credentialsProvider(SystemPropertyCredentialsProvider.create())
            .build();
    private final S3Client s3Client = S3Client.builder()
            .region(region)
            .credentialsProvider(SystemPropertyCredentialsProvider.create())
            .build();

    private static final Long PRE_SIGNED_URL_EXPIRE_MINUTE = 1L;

    public PreSignedUrlVO getUploadPreSignedUrl(final String prefix, final String fileName) {
        final String uuidFileName = getUUIDFile();
        final String key = prefix + "/" + getDateFolder() + "/" + uuidFileName + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        PutObjectPresignRequest preSignedUrlRequest = PutObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_EXPIRE_MINUTE))
                        .putObjectRequest(putObjectRequest)
                        .build();

        return PreSignedUrlVO.of(uuidFileName, getDateFolder(), preSigner.presignPutObject(preSignedUrlRequest).url().toString());
    }

    public String findUrlByName(String path) {
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + path;
    }

    public String getDownloadPreSignedUrl(final String key, final String fileName)  {
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            String replacedFileName = encodedFileName.replaceAll("\\+", "%20");

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .responseContentDisposition("attachment; filename=\"" + replacedFileName + "\"")
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_EXPIRE_MINUTE))
                    .getObjectRequest(getObjectRequest)
                    .build();

            return preSigner.presignGetObject(getObjectPresignRequest).url().toString();

        } catch (UnsupportedEncodingException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    public String makeUploadPrefix(String userUUID, String folder) {
        return String.join("/", userUUID, folder);
    }

    private String getUUIDFile() {
        return UUID.randomUUID().toString();
    }

    private String getDateFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date).replace("-", "/");
    }

    public void deleteFile(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest
                .builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }
}
