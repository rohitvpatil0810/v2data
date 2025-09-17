package com.rohitvpatil0810.v2data.modules.interaction.entity;

import com.rohitvpatil0810.v2data.modules.fileUpload.entity.StoredFile;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Builder
@Table(name = "interactions")
public class Interaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", nullable = false, unique = true)
    private StoredFile file;

    @Column(nullable = true)
    private String notes;

    @Column(nullable = false)
    private String transcription;

    @Column(nullable = false)
    private String generatedNotes;

    @Column(insertable = false, updatable = false)
    private Instant createdAt;
}
