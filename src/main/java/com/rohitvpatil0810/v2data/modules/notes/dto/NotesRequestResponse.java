package com.rohitvpatil0810.v2data.modules.notes.dto;

import com.rohitvpatil0810.v2data.modules.notes.enums.NotesStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesRequestResponse {
    private Long fileId;
    private Long id;
    private NotesStatus notesStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
