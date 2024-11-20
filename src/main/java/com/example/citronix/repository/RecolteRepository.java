package com.example.citronix.repository;

import com.example.citronix.domain.Recolte;
import com.example.citronix.domain.enums.Saison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface RecolteRepository extends JpaRepository<Recolte, UUID> {

    @Query("SELECT r FROM Recolte r WHERE r.saison = :saison AND r.date_de_recolte = :dateDeRecolte")
    Optional<Recolte> findBySaisonAndDateDeRecolte(
            @Param("saison") Saison saison,
            @Param("dateDeRecolte") LocalDateTime dateDeRecolte
    );

    Optional<Recolte> findById(UUID id);
}
