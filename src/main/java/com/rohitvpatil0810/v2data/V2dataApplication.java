package com.rohitvpatil0810.v2data;

import com.rohitvpatil0810.v2data.modules.cloudflareR2.CloudflareR2ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(CloudflareR2ConfigProperties.class)
public class V2dataApplication {

    public static void main(String[] args) {
        SpringApplication.run(V2dataApplication.class, args);
    }

}
