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
//        String fileURL = fileUploadService.uploadFile(file);
        String fileURL = "https://ab405a588dda8bc8bf4b2db984150d2b.r2.cloudflarestorage.com/v2data/c534e3f1-f7a3-4383-b03f-235e3a4e567f-How%20to%20speak%20so%20that%20people%20want%20to%20listen%20%20Julian%20Treasure.mp3?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20250730T171520Z&X-Amz-SignedHeaders=host&X-Amz-Credential=97fb37091e5eadd4b3edab4feaec7761%2F20250730%2Fauto%2Fs3%2Faws4_request&X-Amz-Expires=3600&X-Amz-Signature=d5cabe8e6aa0337c15a9048d9f28e3b104bf05770a46039d1de9f26707d33d63";

        V2DataTranscriberResponseBody v2DataTranscriberResponseBody = v2DataTranscriber.transcribeAndGenerateNotes(fileURL);
        return InteractionResponse.builder()
                .fileURL(fileURL)
                .v2DataTranscriberResponse(v2DataTranscriberResponseBody)
                .build();
    }
}
