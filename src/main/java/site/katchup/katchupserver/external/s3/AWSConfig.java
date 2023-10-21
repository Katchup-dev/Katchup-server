package site.katchup.katchupserver.external.s3;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;


@Configuration
public class AWSConfig {

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String regionString;

    @PostConstruct
    public void setEnv() {
        System.setProperty("aws.accessKeyId", accessKey);
        System.setProperty("aws.secretAccessKey", secretKey);
    }

    @Bean
    public Region getRegion() {
        return Region.of(regionString);
    }

    @Bean
    S3Presigner getS3Presigner() {
        return S3Presigner.builder()
                .region(getRegion())
                .credentialsProvider(SystemPropertyCredentialsProvider.create())
                .build();
    }

    @Bean
    S3Client getS3Client() {
        return S3Client.builder()
                .region(getRegion())
                .credentialsProvider(SystemPropertyCredentialsProvider.create())
                .build();
    }
}
