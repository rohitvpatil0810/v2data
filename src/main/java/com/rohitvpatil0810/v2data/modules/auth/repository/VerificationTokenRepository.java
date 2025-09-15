package com.rohitvpatil0810.v2data.modules.auth.repository;

import com.rohitvpatil0810.v2data.modules.auth.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByHashedToken(String hashedToken);
}
