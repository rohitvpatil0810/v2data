package com.rohitvpatil0810.v2data.modules.notes.dto;

import com.rohitvpatil0810.v2data.modules.notes.enums.NotesStatus;
import lombok.Data;

import java.time.Instant;

@Data
public class NotesWithFileDTO {
    private Long noteId;
    private String notes;
    private String transcription;
    private String generatedNotes;
    private NotesStatus notesStatus;
    private Instant noteCreatedAt;
    private Long fileId;
    private String originalFilename;

    public NotesWithFileDTO(Long noteId,
                            String notes,
                            String transcription,
                            String generatedNotes,
                            NotesStatus notesStatus,
                            Instant noteCreatedAt,
                            Long fileId,
                            String originalFilename) {
        this.noteId = noteId;
        this.notes = notes;
        this.transcription = transcription;
        this.generatedNotes = generatedNotes;
        this.notesStatus = notesStatus;
        this.noteCreatedAt = noteCreatedAt;
        this.fileId = fileId;
        this.originalFilename = originalFilename;
    }
}
