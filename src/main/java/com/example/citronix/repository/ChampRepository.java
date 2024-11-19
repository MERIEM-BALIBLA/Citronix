package com.example.citronix.repository;

import com.example.citronix.domain.Champ;
import com.example.citronix.domain.Ferme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChampRepository extends JpaRepository<Champ, UUID> {
    boolean existsByFermeAndSuperficie(Ferme ferme, double superficie);

}
