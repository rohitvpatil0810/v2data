package com.rohitvpatil0810.v2data.modules.fileUpload.entity;

import com.rohitvpatil0810.v2data.modules.users.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "files")
@Builder
@Data
public class StoredFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFilename;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(nullable = false, unique = true)
    private String storageKey;

    @Column(insertable = false, updatable = false)
    private Instant createdAt;
}
