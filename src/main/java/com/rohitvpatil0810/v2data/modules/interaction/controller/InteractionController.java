package com.rohitvpatil0810.v2data.modules.interaction.controller;

import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.cloudflareR2.CloudflareR2Client;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionRequest;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@RestController
public class InteractionController {

    @Autowired
    CloudflareR2Client cloudflareR2Client;

    @PostMapping(value = "/interact", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<InteractionResponse> echoMessage(@RequestPart("audio") MultipartFile audioFile, @RequestPart("meta") InteractionRequest metaData) throws IOException {
        File file = null;
        try {
            String uploadDir = System.getProperty("user.dir") + "/uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                boolean created = directory.mkdirs();
                if (!created) {
                    throw new IOException("Failed to create upload directory.");
                }
            }

            String uniqueId = UUID.randomUUID().toString();

            String fileName = uniqueId + "-" + audioFile.getOriginalFilename();

            String cleanFileName = StringUtils.cleanPath(Objects.requireNonNull(fileName));

            file = new File(uploadDir + cleanFileName);
            audioFile.transferTo(file);

            cloudflareR2Client.uploadFile("v2data", file);

            String fileURL = cloudflareR2Client.generateSignedUrl("v2data", file.getName());

            InteractionResponse res = InteractionResponse.builder()
                    .fileURL(fileURL)
                    .notes(metaData.getNotes())
                    .build();
            
            return new SuccessResponse<>("Success", res);
        } finally {
            file.delete();
        }

    }
}
