package com.rohitvpatil0810.v2data.modules.v2DataTranscriber.service;

import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.V2DataTranscriberConfigProperties;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto.V2DataTranscriberRequestBody;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto.V2DataTranscriberResponseBody;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class V2DataTranscriber {
    private final V2DataTranscriberConfigProperties v2DataTranscriberConfigProperties;

    private final WebClient webClient;

    public V2DataTranscriber(V2DataTranscriberConfigProperties v2DataTranscriberConfigProperties, WebClient webClient) {
        this.v2DataTranscriberConfigProperties = v2DataTranscriberConfigProperties;
        this.webClient = webClient;
    }

    public V2DataTranscriberResponseBody transcribeAndGenerateNotes(String fileURL) {
        V2DataTranscriberRequestBody v2DataTranscriberRequestBody = V2DataTranscriberRequestBody.builder()
                .audioUrl(fileURL)
                .build();

        return webClient.post()
                .uri(v2DataTranscriberConfigProperties.apiURL())
                .header("X-API-KEY", v2DataTranscriberConfigProperties.apiToken())
                .bodyValue(v2DataTranscriberRequestBody)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    System.out.println("API Error: " + clientResponse.statusCode() + " " + errorBody);
                                    return Mono.error(new RuntimeException("Cloudflare Transcriber AI Worker API failed with status " + clientResponse.statusCode()));
                                })
                )
                .bodyToMono(V2DataTranscriberResponseBody.class)
                .block();
    }
}
