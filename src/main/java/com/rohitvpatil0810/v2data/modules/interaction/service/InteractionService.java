package com.rohitvpatil0810.v2data.modules.interaction.service;

import com.rohitvpatil0810.v2data.modules.fileUpload.service.FileUploadService;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionResponse;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto.V2DataTranscriberResponseBody;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.service.V2DataTranscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class InteractionService {
    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    V2DataTranscriber v2DataTranscriber;

    public InteractionResponse generateNotes(MultipartFile file) throws IOException {
        String fileURL = fileUploadService.uploadFile(file);

        V2DataTranscriberResponseBody v2DataTranscriberResponseBody = v2DataTranscriber.transcribeAndGenerateNotes(fileURL);
        return InteractionResponse.builder()
                .fileURL(fileURL)
                .v2DataTranscriberResponse(v2DataTranscriberResponseBody)
                .build();
    }
}
