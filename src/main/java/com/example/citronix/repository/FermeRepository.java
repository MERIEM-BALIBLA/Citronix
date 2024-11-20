package com.example.citronix.repository;

import com.example.citronix.domain.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FermeRepository extends JpaRepository<Ferme, UUID> {
    Optional<Ferme> findByNom(String nom);
    Optional<Ferme> findById(UUID id);
}
