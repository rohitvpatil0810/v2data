package com.rohitvpatil0810.v2data.modules.notes.service;

import com.rohitvpatil0810.v2data.common.api.exceptions.NotFoundException;
import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import com.rohitvpatil0810.v2data.modules.fileUpload.service.FileUploadService;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequest;
import com.rohitvpatil0810.v2data.modules.notes.dto.NotesRequestResponse;
import com.rohitvpatil0810.v2data.modules.notes.entity.Notes;
import com.rohitvpatil0810.v2data.modules.notes.enums.NotesStatus;
import com.rohitvpatil0810.v2data.modules.notes.event.NotesRequestedEvent;
import com.rohitvpatil0810.v2data.modules.notes.mapper.NotesMapper;
import com.rohitvpatil0810.v2data.modules.notes.repository.NotesRepository;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto.V2DataTranscriberResponseBody;
import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.service.V2DataTranscriber;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NotesService {

    private final FileUploadService fileUploadService;
    private final V2DataTranscriber v2DataTranscriber;
    private final NotesRepository notesRepository;
    private final NotesMapper notesMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public NotesRequestResponse queueNotesRequest(NotesRequest notesRequest) throws NotFoundException {
        String fileUrl = fileUploadService.getSignedUrl(notesRequest.getFileId()).getSignedURL();

        Notes notes = Notes.builder()
                .file(StoredFile.builder().id(notesRequest.getFileId()).build())
                .notesStatus(NotesStatus.IN_QUEUE)
                .notes(notesRequest.getNotes())
                .build();

        Notes savedNotes = notesRepository.save(notes);

        eventPublisher.publishEvent(new NotesRequestedEvent(savedNotes.getId(), notesRequest.getFileId(), fileUrl));

        NotesRequestResponse notesRequestResponse = notesMapper.toNotesRequestResponse(notes);
        notesRequestResponse.setFileId(notesRequest.getFileId());
        return notesRequestResponse;
    }

    public void generateNotes(NotesRequestedEvent notesRequestedEvent) throws NotFoundException {
        Notes notes = notesRepository.findById(notesRequestedEvent.getId()).orElseThrow(
                () -> new NotFoundException("Notes Request cannot be located.")
        );

        notes.setNotesStatus(NotesStatus.PROCESSING);
        notes.setUpdatedAt(Instant.now());
        notesRepository.save(notes);
        try {
            V2DataTranscriberResponseBody responseBody = v2DataTranscriber.transcribeAndGenerateNotes(notesRequestedEvent.getFileUrl());

            notes.setGeneratedNotes(responseBody.getStructuredNotes());
            notes.setTranscription(responseBody.getFullTranscript());
            notes.setNotesStatus(NotesStatus.COMPLETED);
            notes.setUpdatedAt(Instant.now());

            notesRepository.save(notes);
        } catch (RuntimeException ex) {
            notes.setNotesStatus(NotesStatus.FAILED);
            notesRepository.save(notes);
        }


    }
}
