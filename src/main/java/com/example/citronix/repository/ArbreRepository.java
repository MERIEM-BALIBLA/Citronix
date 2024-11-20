package com.example.citronix.repository;

import com.example.citronix.domain.Arbre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArbreRepository extends JpaRepository<Arbre, UUID> {
    Optional<Arbre> findById(UUID id);
}
