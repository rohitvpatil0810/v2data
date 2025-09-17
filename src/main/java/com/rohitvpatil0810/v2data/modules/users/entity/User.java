package com.rohitvpatil0810.v2data.modules.users.entity;

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

    @Column
    private Boolean isEmailVerified;

    @Column(insertable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        if (isEmailVerified == null) {
            isEmailVerified = false;
        }
    }
}