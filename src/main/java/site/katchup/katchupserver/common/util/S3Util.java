package site.katchup.katchupserver.common.util;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import site.katchup.katchupserver.common.exception.InternalServerException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Util {

    public static final String KEY_FILENAME = "fileName";
    public static final String KEY_FILE_UPLOAD_DATE = "fileUploadDate";

    public static final String KEY_PRESIGNED_URL = "preSignedUrl";

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${cloud.aws.region.static}")
    private String location;

    public HashMap<String, String> generatePreSignedUrl(String prefix, String fileName) {
        HashMap<String, String> result = new HashMap<>();
        String uuidFileName = getUUIDFile();
        result.put(KEY_FILENAME, uuidFileName);
        String filePath = prefix + "/" + getDateFolder() + "/" + uuidFileName + fileName;
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, filePath);
        result.put(KEY_PRESIGNED_URL, amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString());
        result.put(KEY_FILE_UPLOAD_DATE, getDateFolder());
        return result;
    }

    public String findUrlByName(String path) {
        return "https://" + bucket + ".s3." + location + ".amazonaws.com/" + path;
    }

    public String getDownloadPreSignedUrl(String filePath, String fileName) {
        try {
            String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
            String replacedFileName = encodedFileName.replaceAll("\\+", "%20");

            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucket, filePath)
                            .withMethod(HttpMethod.GET) // HTTP GET 요청
                            .withResponseHeaders(new ResponseHeaderOverrides().withContentDisposition("attachment; filename=\"" + replacedFileName + "\""))
                            .withExpiration(getPreSignedUrlExpiration());

            return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        } catch (UnsupportedEncodingException e) {
            throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
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
        expTimeMillis += 1000 * 30;
        expiration.setTime(expTimeMillis);
        return expiration;
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

    public void deleteFile(String fileKey) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileKey);
        amazonS3.deleteObject(request);
    }
}
