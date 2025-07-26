package com.rohitvpatil0810.v2data.modules.cloudflareR2;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

@Component
public class CloudflareR2Client {
    private final S3Client s3Client;


    public CloudflareR2Client(CloudflareR2ConfigProperties config) {
        System.out.println(config.toString());
        this.s3Client = buildS3Client(config);
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
}
