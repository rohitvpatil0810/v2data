package com.rohitvpatil0810.v2data.modules.interaction.service;

import com.rohitvpatil0810.v2data.modules.fileUpload.dto.FileUploadResponse;
import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import com.rohitvpatil0810.v2data.modules.fileUpload.service.FileUploadService;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionResponse;
import com.rohitvpatil0810.v2data.modules.interaction.entity.Interaction;
import com.rohitvpatil0810.v2data.modules.interaction.repository.InteractionRepository;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto.V2DataTranscriberResponseBody;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.service.V2DataTranscriber;
import jakarta.persistence.EntityManager;
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

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private EntityManager entityManager;

    public InteractionResponse generateNotes(MultipartFile file, String notes) throws IOException {
        FileUploadResponse fileUploadResponse = fileUploadService.uploadFile(file);

        V2DataTranscriberResponseBody v2DataTranscriberResponseBody = v2DataTranscriber.transcribeAndGenerateNotes(fileUploadResponse.getSignedURL());

        StoredFile fileRecord = entityManager.getReference(StoredFile.class, fileUploadResponse.getId());

        Interaction interactionRecord = Interaction.builder()
                .file(fileRecord)
                .notes(notes)
                .transcription(v2DataTranscriberResponseBody.getFullTranscript())
                .generatedNotes(v2DataTranscriberResponseBody.getStructuredNotes())
                .build();

        interactionRepository.save(interactionRecord);

        return InteractionResponse.builder()
                .fileId(fileRecord.getId())
                .interactionId(interactionRecord.getId())
                .notes(notes)
                .fileURL(fileUploadResponse.getSignedURL())
                .v2DataTranscriberResponse(v2DataTranscriberResponseBody)
                .build();
    }
}
