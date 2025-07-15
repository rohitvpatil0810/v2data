package com.rohitvpatil0810.v2data.modules.interaction.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionRequest;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionResponse;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
public class InteractionController {

    @PostMapping(value = "/interact", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<InteractionResponse> echoMessage(@RequestPart("audio") MultipartFile audioFile, @RequestPart("meta") InteractionRequest metaData) throws BadRequestException, IOException {

        String fileName = audioFile.getOriginalFilename();

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("Failed to create upload directory.");
            }
        }

        String cleanFileName = StringUtils.cleanPath(Objects.requireNonNull(audioFile.getOriginalFilename()));
        File destination = new File(uploadDir + cleanFileName);
        audioFile.transferTo(destination);

        InteractionResponse res = InteractionResponse.builder().fileName(fileName).notes(metaData.getNotes()).build();

        return new SuccessResponse<>("Success", res);
    }
}
