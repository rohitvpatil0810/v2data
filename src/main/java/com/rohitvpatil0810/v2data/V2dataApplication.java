package com.rohitvpatil0810.v2data;

import com.rohitvpatil0810.v2data.modules.cloudflareR2.CloudflareR2ConfigProperties;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.V2DataTranscriberConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableConfigurationProperties({CloudflareR2ConfigProperties.class, V2DataTranscriberConfigProperties.class})
public class V2dataApplication {

    public static void main(String[] args) {
        SpringApplication.run(V2dataApplication.class, args);
    }

}
