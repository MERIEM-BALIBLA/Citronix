package com.example.citronix.repository;

import com.example.citronix.domain.RecoltesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecoltesDetailsRepository extends JpaRepository<RecoltesDetails, UUID> {
    Optional<RecoltesDetails> findById(UUID id);
}
