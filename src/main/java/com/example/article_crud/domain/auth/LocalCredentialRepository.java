package com.example.article_crud.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LocalCredentialRepository extends JpaRepository<LocalCredential, UUID> {

    Optional<LocalCredential> findByEmail(String email);

    @Query("SELECT lc FROM LocalCredential lc WHERE lc.email = :email AND lc.isActive = true")
    Optional<LocalCredential> findByEmailAndIsActiveTrue(String email);

}
