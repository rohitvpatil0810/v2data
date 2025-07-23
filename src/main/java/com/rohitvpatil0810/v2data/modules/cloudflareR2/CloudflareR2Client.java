package com.rohitvpatil0810.v2data.modules.cloudflareR2;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
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
                .build();

        URI endpoint = URI.create(String.format("https://%s.r2.cloudflarestorage.com", config.accountId()));

        return S3Client.builder()
                .endpointOverride(endpoint)
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of("auto"))
                .serviceConfiguration(serviceConfiguration)
                .build();
    }

    public String uploadFile(String bucketName, File file) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file.getName())
                .build();

        System.out.println(putObjectRequest.toString());
        RequestBody requestBody = RequestBody.fromFile(file);
        PutObjectResponse response = this.s3Client.putObject(putObjectRequest, requestBody);


        return response.toString();
    }
}
