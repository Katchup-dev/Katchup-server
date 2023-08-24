package site.katchup.katchupserver.common.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {

    public static final String KEY_FILENAME = "fileName";
    public static final String KEY_PRESIGNED_URL = "preSignedUrl";

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public HashMap<String, String> generatePreSignedUrl(String prefix, String fileName) {
        HashMap<String, String> result = new HashMap<>();
        String uuidFileName = getUUIDFile();
        result.put(KEY_FILENAME, uuidFileName);
        String filePath = prefix + "/" + uuidFileName + fileName;
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, filePath);
        result.put(KEY_PRESIGNED_URL, amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());
        return result;
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(getPreSignedUrlExpiration());

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    public String makeUploadPrefix(String userUUID, String folder) {
        return String.join("/", userUUID, folder, getDateFolder());
    }

    private String getUUIDFile() {
        return UUID.randomUUID().toString();
    }

    private String getDateFolder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return sdf.format(date).replace("-", "/");
    }

    public void deleteFile(String filePath) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, filePath);
        amazonS3.deleteObject(request);
    }
}
