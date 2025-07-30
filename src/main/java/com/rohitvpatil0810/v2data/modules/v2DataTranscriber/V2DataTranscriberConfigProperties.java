package com.rohitvpatil0810.v2data.modules.v2DataTranscriber;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("v2data-transcriber")
public record V2DataTranscriberConfigProperties(String apiURL, String apiToken) {
}
