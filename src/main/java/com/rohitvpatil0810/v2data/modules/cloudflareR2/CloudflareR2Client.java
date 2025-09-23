package com.rohitvpatil0810.v2data.modules.cloudflareR2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

@Component
@Slf4j
public class CloudflareR2Client {
    private final S3Client s3Client;
    private final S3Presigner presigner;


    public CloudflareR2Client(CloudflareR2ConfigProperties config) {
        this.s3Client = buildS3Client(config);
        this.presigner = buildS3Presigner(config);
    }

    private static S3Presigner buildS3Presigner(CloudflareR2ConfigProperties config) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.accessKey(),
                config.secretKey()
        );

        URI endpoint = URI.create(String.format("https://%s.r2.cloudflarestorage.com", config.accountId()));

        return S3Presigner.builder()
                .endpointOverride(endpoint)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build())
                .build();
    }

    private static S3Client buildS3Client(CloudflareR2ConfigProperties config) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                config.accessKey(),
                config.secretKey()
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
                .pathStyleAccessEnabled(true)
                .chunkedEncodingEnabled(false) // ❗ Important for large files
                .build();

        URI endpoint = URI.create(String.format("https://%s.r2.cloudflarestorage.com", config.accountId()));

        return S3Client.builder()
                .endpointOverride(endpoint)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    public String uploadFile(String bucketName, File file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getName())
                .build();

        RequestBody requestBody = RequestBody.fromInputStream(new FileInputStream(file), file.length());

        PutObjectResponse response = this.s3Client.putObject(putObjectRequest, requestBody);
        return response.toString();
    }

    public String uploadFile(String bucketName, MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getOriginalFilename())
                .build();

        RequestBody requestBody = RequestBody.fromBytes(file.getBytes());

        PutObjectResponse response = this.s3Client.putObject(putObjectRequest, requestBody);
        return response.toString();
    }

    public String generateSignedUrl(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(getObjectRequest)
                .build();

        URL presignedURL = presigner.presignGetObject(presignRequest).url();

        return presignedURL.toString();
    }

    public void deleteFile(String bucketName, String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        this.s3Client.deleteObject(deleteObjectRequest);

        log.debug("File deleted - {} - {}", bucketName, key);
    }
}
