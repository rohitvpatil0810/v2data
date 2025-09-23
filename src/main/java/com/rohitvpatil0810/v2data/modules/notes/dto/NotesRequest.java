package com.rohitvpatil0810.v2data.modules.notes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotesRequest {
    @NotNull(message = "fileId cannot be null")
    private Long fileId;

    private String notes;
}
