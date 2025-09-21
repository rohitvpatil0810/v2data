package com.rohitvpatil0810.v2data.modules.auth.repository;

import com.rohitvpatil0810.v2data.modules.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByHashedRefreshToken(String hashedRefreshToken);

    void deleteByHashedRefreshToken(String hashedRefreshToken);
}
