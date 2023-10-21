package site.katchup.katchupserver.external.s3;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class DownloadPreSignedUrlVO {
    private String preSignedUrl;
}
