package com.rohitvpatil0810.v2data.modules.notes.entity;

import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import com.rohitvpatil0810.v2data.modules.notes.enums.NotesStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false, unique = true)
    private StoredFile file;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = true)
    private String transcription;

    @Column(nullable = true)
    private String generatedNotes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotesStatus notesStatus;

    @Column(insertable = false, updatable = false)
    private Instant createdAt;

    @Column(insertable = false, updatable = true)
    private Instant updatedAt;
}
