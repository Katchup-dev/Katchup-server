package site.katchup.katchupserver.common.util;

import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import site.katchup.katchupserver.config.AwsS3Config;

import java.io.InputStream;
import java.net.URLDecoder;

@Configuration
@RequiredArgsConstructor
public class S3Util {

    private final Environment env;

    private final AwsS3Config awsS3Config;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    // 파일 업로드
    public String upload(InputStream file, String fileName, ObjectMetadata objectMetadata) {
        awsS3Config.amazonS3Client().putObject(bucket, fileName, file, objectMetadata);
        System.out.println("upload 성공!!");
        String url = awsS3Config.amazonS3Client().getUrl(bucket, fileName).toString();
        System.out.println("return URL : "+ url);
        return url;
    }

    // 파일 삭제
    public void delete(String fileName) {
        String key = URLDecoder.decode(fileName.split("/")[3]);
        awsS3Config.amazonS3Client().deleteObject(bucket, key);
    }

    public String getImageUrl(String filePath) {
        return awsS3Config.amazonS3Client().getUrl(bucket, filePath).toString();
    }
}