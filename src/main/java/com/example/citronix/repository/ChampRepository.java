package com.example.citronix.repository;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChampRepository extends JpaRepository<Champ, UUID> {
    Optional<Champ> findByNom(String nom);

    Optional<Champ> findByFerme(Ferme ferme);

    Optional<Champ> findById(UUID id);

    int countByFerme(Ferme ferme);
}
