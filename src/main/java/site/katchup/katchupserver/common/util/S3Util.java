package site.katchup.katchupserver.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import site.katchup.katchupserver.config.AwsS3Config;

import java.net.URLDecoder;

@Configuration
@RequiredArgsConstructor
public class S3Util {

    private final Environment env;

    private final AwsS3Config awsS3Config;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    // 파일 업로드
    public String upload(String fileName, String filePath) {
        awsS3Config.amazonS3Client().putObject(bucket, fileName, filePath);
        return awsS3Config.amazonS3Client().getUrl(bucket, fileName).toString();
    }

    // 파일 삭제
    public void delete(String fileName) {
        String key = URLDecoder.decode(fileName.split("/")[3]);
        awsS3Config.amazonS3Client().deleteObject(bucket, key);
    }
}