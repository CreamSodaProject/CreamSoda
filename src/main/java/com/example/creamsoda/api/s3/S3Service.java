package com.example.creamsoda.api.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class S3Service {
    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

//    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(String fileName, MultipartFile file) throws IOException {
        s3Client.putObject(new PutObjectRequest(bucket, getFileNameServer(file), file.getInputStream(),null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }

    private static String getFileNameServer(MultipartFile multipartFile) {
        // 파일 확장자 추출
        int pos = multipartFile.getOriginalFilename().lastIndexOf(".");
        String ext = multipartFile.getOriginalFilename().substring(pos + 1);

        // 서버에 올라갈 파일명 반환
        return makeFileName() + "." + ext;
    }

    public static String makeFileName() {
        Date now = new Date();
        String today = new SimpleDateFormat("yyyyMMddHHmmss").format(now);

        StringBuilder random = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            char ch = (char) ((Math.random() * 26) + 97);
            random.append(ch);
        }

        return today + random;
    }
}