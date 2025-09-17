package com.rohitvpatil0810.v2data.modules.auth.entity;

import com.rohitvpatil0810.v2data.modules.users.entity.User;
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
        name = "verification_tokens",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"token"}
        )
)
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String hashedToken;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column
    private Boolean used;

    @Column(nullable = false, insertable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    public void onCreate() {
        if (this.used == null) {
            this.used = Boolean.FALSE;
        }
    }
}
