package com.rohitvpatil0810.v2data.modules.cloudflareR2;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cloudflare-r2")
public record CloudflareR2ConfigProperties(String accountId, String accessKey, String secretKey, String token,
                                           String bucketName) {
}
