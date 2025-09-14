package com.rohitvpatil0810.v2data.modules.users.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"email"}
        )
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private Boolean isEmailVerified;

    @Column(nullable = true)
    private LocalDateTime lastLogoutTimestamp;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;
}