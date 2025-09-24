package com.rohitvpatil0810.v2data.modules.notes.dto;

import com.rohitvpatil0810.v2data.modules.notes.enums.NotesStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class NotesWithFileDTO {
    private Long id;
    private String notes;
    private String transcription;
    private String generatedNotes;
    private NotesStatus notesStatus;
    private Instant createdAt;
    private Long fileId;
    private String originalFilename;

    public NotesWithFileDTO(Long id,
                            String notes,
                            String transcription,
                            String generatedNotes,
                            NotesStatus notesStatus,
                            Instant createdAt,
                            Long fileId,
                            String originalFilename) {
        this.id = id;
        this.notes = notes;
        this.transcription = transcription;
        this.generatedNotes = generatedNotes;
        this.notesStatus = notesStatus;
        this.createdAt = createdAt;
        this.fileId = fileId;
        this.originalFilename = originalFilename;
    }
}
