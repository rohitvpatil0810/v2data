package com.rohitvpatil0810.v2data.modules.interaction.controller;

import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionRequest;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionResponse;
import com.rohitvpatil0810.v2data.modules.interaction.service.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class InteractionController {

    @Autowired
    InteractionService interactionService;

    @PostMapping(value = "/interact", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<InteractionResponse> echoMessage(@RequestPart("audio") MultipartFile audioFile, @RequestPart("meta") InteractionRequest metaData) throws IOException {
        String fileURL = interactionService.generateNotes(audioFile);

        InteractionResponse res = InteractionResponse.builder()
                .fileURL(fileURL)
                .notes(metaData.getNotes())
                .build();

        return new SuccessResponse<>("Success", res);
    }
}
