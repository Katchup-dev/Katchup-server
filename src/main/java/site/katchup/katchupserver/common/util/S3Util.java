package site.katchup.katchupserver.common.util;

import com.amazonaws.services.s3.AmazonS3Client;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final Environment env;
    private final AmazonS3Client amazonS3Client;
    private final String bucket = env.getProperty("s3.bucket");

    // 파일 업로드
    public String upload(String fileName, String filePath) {
        amazonS3Client.putObject(bucket, fileName, filePath);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 파일 삭제
    public void delete(String fileName) {
        String key = URLDecoder.decode(fileName.split("/")[3]);
        amazonS3Client.deleteObject(bucket, key);
    }
}
