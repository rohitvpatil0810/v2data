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
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotesService {

    private final FileUploadService fileUploadService;
    private final V2DataTranscriber v2DataTranscriber;
    private final NotesRepository notesRepository;
    private final NotesMapper notesMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final EntityManager entityManager;

    @Transactional
    public NotesRequestResponse queueNotesRequest(NotesRequest notesRequest) throws NotFoundException {
        String fileUrl = fileUploadService.getSignedUrl(notesRequest.getFileId()).getSignedURL();

        Notes notes = Notes.builder()
                .file(StoredFile.builder().id(notesRequest.getFileId()).build())
                .notesStatus(NotesStatus.IN_QUEUE)
                .notes(notesRequest.getNotes())
                .build();

        Notes savedNotes = notesRepository.save(notes);
        entityManager.refresh(savedNotes);
        eventPublisher.publishEvent(new NotesRequestedEvent(savedNotes.getId(), notesRequest.getFileId(), fileUrl));

        return notesMapper.toNotesRequestResponse(savedNotes);
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
